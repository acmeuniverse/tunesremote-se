/*
 * This file is part of FireflyClient.
 *
 * FireflyClient is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * FireflyClient is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FireflyClient; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * Copyright 2007 Vincent Cariven
 */
package net.firefly.client.gui.swing.tree;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.plaf.TreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.firefly.client.controller.ResourceManager;
import net.firefly.client.controller.request.PlaylistRequestManager;
import net.firefly.client.gui.context.Context;
import net.firefly.client.gui.swing.dialog.ErrorDialog;
import net.firefly.client.gui.swing.table.dnd.SongTransferable;
import net.firefly.client.gui.swing.tree.editor.PlaylistTreeCellEditor;
import net.firefly.client.gui.swing.tree.listeners.PlaylistTreeSelectionListener;
import net.firefly.client.gui.swing.tree.menu.PlaylistContextMenu;
import net.firefly.client.gui.swing.tree.model.PlaylistTreeModel;
import net.firefly.client.gui.swing.tree.renderer.PlaylistTreeCellRenderer;
import net.firefly.client.gui.swing.tree.ui.PlaylistTreeUI;
import net.firefly.client.model.data.SongContainer;
import net.firefly.client.model.playlist.IPlaylist;
import net.firefly.client.model.playlist.PlaylistStatus;
import net.firefly.client.model.playlist.SmartPlaylist;
import net.firefly.client.model.playlist.StaticPlaylist;
import net.firefly.client.tools.FireflyClientException;

public class PlaylistTree extends JTree implements TreeModelListener, Autoscroll,
		DropTargetListener {

	private static final long serialVersionUID = -2387433539948894006L;

	protected Context context;

	protected JPanel container;
	
	protected Frame rootContainer;

	protected PlaylistContextMenu contextMenu;

	private int margin = 12; // for autoscroll

	protected DropTarget dropTarget;

	private int highlightRow = -1;
	
	public PlaylistTree(Context context, JPanel container, Frame rootContainer) {
		super(new PlaylistTreeModel(context, rootContainer));
		((PlaylistTreeModel) (getModel())).setTree(this);
		this.context = context;
		this.container = container;
		this.rootContainer = rootContainer;
		initialize();
	}

	public void initialize() {
		getModel().addTreeModelListener(this);

		// -- set selection model
		TreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setSelectionModel(treeSelectionModel);

		// -- hide root
		setRootVisible(false);

		// -- set cell renderer
		PlaylistTreeCellRenderer renderer = new PlaylistTreeCellRenderer();
		setCellRenderer(renderer);

		// -- set cell editor for pure static playlists
		setEditable(true);
		setCellEditor(new PlaylistTreeCellEditor(rootContainer, this, renderer, context));

		setRowHeight(0);

		super.setUI(new PlaylistTreeUI());

		setShowsRootHandles(false);
		setToggleClickCount(0);

		addTreeSelectionListener(new PlaylistTreeSelectionListener(this, context));

		// -- disable keyboard navigation
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				e.consume();
			}

			public void keyReleased(KeyEvent e) {
				e.consume();
			}

			public void keyTyped(KeyEvent e) {
				e.consume();
			}
		});

		// -- add right click management

		contextMenu = new PlaylistContextMenu(context, this, rootContainer);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (SwingUtilities.isRightMouseButton(evt) && PlaylistRequestManager.supportPlaylistAdvancedManagement(context.getServerVersion())) {
					JTree tree = (JTree) evt.getSource();
					TreePath path = tree.getPathForLocation(evt.getX(), evt.getY());
					if (path != null) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
						Object value = node.getUserObject();
						if (value instanceof IPlaylist) {
							IPlaylist p = (IPlaylist) value;
							setSelectionPath(path);
							if (value instanceof StaticPlaylist || value instanceof SmartPlaylist) {
								if (p.getStatus() == PlaylistStatus.LOADED) {
									setSelectionPath(path);
									contextMenu.setCurrentTreePath(path);
									contextMenu.show(tree, evt.getX(), evt.getY());
								}
							}
						}
					}
				}
			}
		});

		// -- add droppable behaviour
		dropTarget = new DropTarget(this, this);
	}

	public void expandAll() {
		Enumeration e = ((DefaultMutableTreeNode) getModel().getRoot()).depthFirstEnumeration();
		for (; e.hasMoreElements();) {
			TreePath t = new TreePath(((DefaultMutableTreeNode) e.nextElement()).getPath());
			setExpandedState(t, true);
		}
	}

	// -- TreeModelListener
	public void treeStructureChanged(TreeModelEvent treemodelevent) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				expandAll();
				// set the library selected by default
				setSelectionPath(getPathForRow(1));
			}
		});
	}

	// -- ensure that only library/playlist is selected
	// -- moreover: manage playlist lazy load
	public void setSelectionPath(TreePath path) {
		int depth = 0;
		if (path != null) {
			depth = path.getPathCount() - 1;
		} else {
			depth = 0;
		}
		if (depth > 1) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
			if (node != null) {
				Object o = node.getUserObject();
				if (o instanceof IPlaylist) {
					// a playlist is selected
					IPlaylist pl = (IPlaylist) o;
					PlaylistStatus ps = pl.getStatus();
					if (ps == PlaylistStatus.NOT_LOADED) {
						// -- load the playlist
						Thread t = new Thread(new PlaylistLoader(path, pl), "[PlaylitsLoader]");
						t.start();
					} else if (ps == PlaylistStatus.LOADED) {
						super.setSelectionPath(path);
					} else {
						// -- the playlist is loading: do nothing
						;
					}
				} else {
					super.setSelectionPath(path);
				}
			}
		}
	}

	public void setUI(TreeUI ui) {
		;
	}

	public Color getBackground() {
		if (container != null) {
			return container.getBackground();
		} else {
			return super.getBackground();
		}
	}

	public void treeNodesChanged(TreeModelEvent treemodelevent) {
	}

	public void treeNodesInserted(TreeModelEvent treemodelevent) {
	}

	public void treeNodesRemoved(TreeModelEvent treemodelevent) {
	}

	// -- ensures that there is no automatic horizontal scrolls that cannot be
	// scrolled back (no horizontal scrollbar)
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	public void autoscroll(Point p) {
		// int realrow = getRowForLocation(p.x, p.y);
		int realrow = getRowForLocation(20, p.y);
		Rectangle outer = getBounds();
		realrow = (p.y + outer.y <= margin ? realrow < 1 ? 0 : realrow - 1 : realrow < getRowCount() - 1 ? realrow + 1
				: realrow);
		scrollRowToVisible(realrow);
	}

	public Insets getAutoscrollInsets() {
		Rectangle outer = getBounds();
		Rectangle inner = getParent().getBounds();
		return new Insets(inner.y - outer.y + margin, inner.x - outer.x, outer.height - inner.height - inner.y + outer.y
				+ margin, outer.width - inner.width - inner.x + outer.x);
	}

	class PlaylistLoader implements Runnable {
		TreePath path;
		IPlaylist pl;

		public PlaylistLoader(TreePath path, IPlaylist pl) {
			this.path = path;
			this.pl = pl;
		}

		public void run() {
			// -- following thread usage avoid to have some strange display
			// behaviours for nearly instantaneous loads
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(100);
						getModel().valueForPathChanged(path, pl);
					} catch (Exception e) {
					}
				}
			});
			t.start();
			try {
				context.getPlaylistRequestManager().loadSongListForPlaylist(pl, context.getLibraryInfo().getHost(), context
						.getLibraryInfo().getPort(), "", "",
						context.getLibraryInfo().getPassword());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						PlaylistTree.this.setSelectionPath(path);
						PlaylistTree.this.scrollPathToVisible(path);
					}
				});
			} catch (FireflyClientException ex) {
				ex.printStackTrace();
			} finally {
				getModel().valueForPathChanged(path, pl);
			}

		}
	}

	// -- DRAG AND DROP MANAGEMENT
	public void dragEnter(DropTargetDragEvent evt) {
	}

	public void dragExit(DropTargetEvent evt) {
		highlightRow = -1;
		repaint();
	}

	public void dragOver(DropTargetDragEvent evt) {
		if (!PlaylistRequestManager.supportPlaylistAdvancedManagement(context.getServerVersion())){
			evt.rejectDrag();
			return;
		}
		Point mousePoint = evt.getLocation();
		if (mousePoint != null) {
			TreePath path = getPathForLocation(mousePoint.x, mousePoint.y);
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object value = node.getUserObject();
				if (value instanceof StaticPlaylist && ((StaticPlaylist) value).getStatus() == PlaylistStatus.LOADED
						&& !path.equals(getSelectionPath())) {
					// highlight node
					highlightRow = getRowForPath(path);
					evt.acceptDrag(DnDConstants.ACTION_COPY);
				} else {
					highlightRow = -1;
					evt.rejectDrag();
				}
			} else {
				evt.rejectDrag();
			}
			repaint();
		}
	}

	public void drop(DropTargetDropEvent evt) {
		if (!PlaylistRequestManager.supportPlaylistAdvancedManagement(context.getServerVersion())){
			return;
		}
		Point mousePoint = evt.getLocation();
		if (mousePoint != null) {
			TreePath path = getPathForLocation(mousePoint.x, mousePoint.y);
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object value = node.getUserObject();
				if (value instanceof StaticPlaylist) {
					highlightRow = -1;
					Transferable t = evt.getTransferable();
					StaticPlaylist p = (StaticPlaylist) value;
					try {
						SongContainer[] songs = (SongContainer[]) t.getTransferData(SongTransferable.songsArrayFlavor);
						context.getPlaylistRequestManager().addSongsToStaticPlaylist(p.getPlaylistId(), songs, 
								context.getLibraryInfo().getHost(), context.getLibraryInfo().getPort(), 
								"", "", 
								context.getLibraryInfo().getPassword());
						for (int i = 0; i < songs.length; i++) {
							p.addSong(songs[i]);
						}
					} catch (Exception e) {
						ErrorDialog.showDialog(rootContainer, ResourceManager.getLabel("add.songs.to.playlist.unexpected.error.message",
								context.getConfig().getLocale()), ResourceManager.getLabel(
								"add.songs.to.playlist.unexpected.error.title", context.getConfig().getLocale()), e, context.getConfig()
								.getLocale());
					} finally {
						repaint();
					}
				}
			}
		}

	}

	public void dropActionChanged(DropTargetDragEvent evt) {
	}

	public int getHighlightRow() {
		return highlightRow;
	}
}