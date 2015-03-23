### DAAP ContentCodes ###
Inspired by http://www.deleet.de/projekte/daap/?ContentCodes I ran the GET /content-codes? query using TunesRemoteSE against latest iTunes 10.3.1.  The following list was returned.  Unfortunately it doesn't seem to contain the dacp specific codes, but the field names are useful for querying the database :

| **Code** | **Name** | **Type** |
|:---------|:---------|:---------|
| abpl | daap.baseplaylist | 1 |
| aeHD | com.apple.itunes.is-hd-video | 1 |
| aeHV | com.apple.itunes.has-video | 1 |
| aeMK | com.apple.itunes.mediakind | 1 |
| aePC | com.apple.itunes.is-podcast | 1 |
| aePP | com.apple.itunes.is-podcast-playlist | 1 |
| aePS | com.apple.itunes.special-playlist | 1 |
| aeSG | com.apple.itunes.saved-genius | 1 |
| aeSP | com.apple.itunes.smart-playlist | 1 |
| aprm | daap.playlistrepeatmode | 1 |
| apsm | daap.playlistshufflemode | 1 |
| asbk | daap.bookmarkable | 1 |
| asco | daap.songcompilation | 1 |
| ascr | daap.songcontentrating | 1 |
| asdb | daap.songdisabled | 1 |
| asdk | daap.songdatakind | 1 |
| asgp | daap.songgapless | 1 |
| ashp | daap.songhasbeenplayed | 1 |
| asur | daap.songuserrating | 1 |
| fï¿½ch | dmap.haschildcontainers | 1 |
| mikd | dmap.itemkind | 1 |
| msal | dmap.supportsautologout | 1 |
| msau | dmap.authenticationmethod | 1 |
| msbr | dmap.supportsbrowse | 1 |
| msex | dmap.supportsextensions | 1 |
| msix | dmap.supportsindex | 1 |
| mslr | dmap.loginrequired | 1 |
| mspi | dmap.supportspersistentids | 1 |
| msqy | dmap.supportsquery | 1 |
| msrs | dmap.supportsresolve | 1 |
| msup | dmap.supportsupdate | 1 |
| muty | dmap.updatetype | 1 |
|  | com.apple.itunes.req-fplay | 1 |
| asrv | daap.songrelativevolume | 2 |
| ceJC | com.apple.itunes.jukebox-client-vote | 2 |
| asac | daap.songartworkcount | 3 |
| asbr | daap.songbitrate | 3 |
| asbt | daap.songbeatsperminute | 3 |
| asdc | daap.songdisccount | 3 |
| asdn | daap.songdiscnumber | 3 |
| ased | daap.songextradata | 3 |
| asgr | daap.supportsgroups | 3 |
| astc | daap.songtrackcount | 3 |
| astn | daap.songtracknumber | 3 |
| asyr | daap.songyear | 3 |
| ated | daap.supportsextradata | 3 |
| mcty | dmap.contentcodestype | 3 |
| ceJS | com.apple.itunes.jukebox-score | 4 |
| aeAI | com.apple.itunes.itms-artistid | 5 |
| aeCI | com.apple.itunes.itms-composerid | 5 |
| aeCS | com.apple.itunes.artworkchecksum | 5 |
| aeDP | com.apple.itunes.drm-platform-id | 5 |
| aeDV | com.apple.itunes.drm-versions | 5 |
| aeES | com.apple.itunes.episode-sort | 5 |
| aeGD | com.apple.itunes.gapless-enc-dr | 5 |
| aeGE | com.apple.itunes.gapless-enc-del | 5 |
| aeGH | com.apple.itunes.gapless-heur | 5 |
| aeGI | com.apple.itunes.itms-genreid | 5 |
| aeMC | com.apple.itunes.playlist-contains-media-type-count | 5 |
| aeMk | com.apple.itunes.extended-media-kind | 5 |
| aeNV | com.apple.itunes.norm-volume | 5 |
| aePI | com.apple.itunes.itms-playlistid | 5 |
| aeRD | com.apple.itunes.rental-duration | 5 |
| aeRP | com.apple.itunes.rental-pb-start | 5 |
| aeRS | com.apple.itunes.rental-start | 5 |
| aeRU | com.apple.itunes.rental-pb-duration | 5 |
| aeSF | com.apple.itunes.itms-storefrontid | 5 |
| aeSI | com.apple.itunes.itms-songid | 5 |
| aeSU | com.apple.itunes.season-num | 5 |
| aeSV | com.apple.itunes.music-sharing-version | 5 |
| agac | daap.groupalbumcount | 5 |
| agma | daap.groupmatchedqueryalbumcount | 5 |
| agmi | daap.groupmatchedqueryitemcount | 5 |
| asbo | daap.songbookmark | 5 |
| ascd | daap.songcodectype | 5 |
| ascs | daap.songcodecsubtype | 5 |
| askp | daap.songuserskipcount | 5 |
| aspc | daap.songuserplaycount | 5 |
| assp | daap.songstoptime | 5 |
| assr | daap.songsamplerate | 5 |
| asst | daap.songstarttime | 5 |
| assz | daap.songsize | 5 |
| astm | daap.songtime | 5 |
| asvc | daap.songprimaryvideocodec | 5 |
| ceJI | com.apple.itunes.jukebox-current | 5 |
| ceJV | com.apple.itunes.jukebox-vote | 5 |
| mcnm | dmap.contentcodesnumber | 5 |
| mctc | dmap.containercount | 5 |
| mcti | dmap.containeritemid | 5 |
| meds | dmap.editcommandssupported | 5 |
| miid | dmap.itemid | 5 |
| mimc | dmap.itemcount | 5 |
| mlid | dmap.sessionid | 5 |
| mpco | dmap.parentcontainerid | 5 |
| mrco | dmap.returnedcount | 5 |
| msas | dmap.authenticationschemes | 5 |
| msdc | dmap.databasescount | 5 |
| mstm | dmap.timeoutinterval | 5 |
| mstt | dmap.status | 5 |
| mtco | dmap.specifiedtotalcount | 5 |
| musr | dmap.serverrevision | 5 |
|  | com.apple.itunes.playlist-contains-media-type | 5 |
| msto | dmap.utcoffset | 6 |
| aeDR | com.apple.itunes.drm-user-id | 7 |
| aeGR | com.apple.itunes.gapless-resy | 7 |
| aeGU | com.apple.itunes.gapless-dur | 7 |
| aeK1 | com.apple.itunes.drm-key1-id | 7 |
| aeK2 | com.apple.itunes.drm-key2-id | 7 |
| aeND | com.apple.itunes.non-drm-user-id | 7 |
| aeSE | com.apple.itunes.store-pers-id | 7 |
| asai | daap.songalbumid | 7 |
| asls | daap.songlongsize | 7 |
| asri | daap.songartistid | 7 |
| mper | dmap.persistentid | 7 |
| mrpr | dmap.remotepersistentid | 7 |
| aeCR | com.apple.itunes.content-rating | 9 |
| aeEN | com.apple.itunes.episode-num-str | 9 |
| aeMX | com.apple.itunes.movie-info-xml | 9 |
| aeNN | com.apple.itunes.network-name | 9 |
| aeSN | com.apple.itunes.series-name | 9 |
| aeXD | com.apple.itunes.xid | 9 |
| agrp | daap.songgrouping | 9 |
| asaa | daap.songalbumartist | 9 |
| asal | daap.songalbum | 9 |
| asar | daap.songartist | 9 |
| ascm | daap.songcomment | 9 |
| ascn | daap.songcontentdescription | 9 |
| ascp | daap.songcomposer | 9 |
| asct | daap.songcategory | 9 |
| asdt | daap.songdescription | 9 |
| aseq | daap.songeqpreset | 9 |
| asfm | daap.songformat | 9 |
| asgn | daap.songgenre | 9 |
| asky | daap.songkeywords | 9 |
| aslc | daap.songlongcontentdescription | 9 |
| aspu | daap.songpodcasturl | 9 |
| assa | daap.sortartist | 9 |
| assc | daap.sortcomposer | 9 |
| assl | daap.sortalbumartist | 9 |
| assn | daap.sortname | 9 |
| asss | daap.sortseriesname | 9 |
| assu | daap.sortalbum | 9 |
| asul | daap.songdataurl | 9 |
| mcna | dmap.contentcodesname | 9 |
| minm | dmap.itemname | 9 |
| minm | dmap.itemname | 9 |
| msts | dmap.statusstring | 9 |
| asda | daap.songdateadded | 10 |
| asdm | daap.songdatemodified | 10 |
| asdp | daap.songdatepurchased | 10 |
| asdr | daap.songdatereleased | 10 |
| askd | daap.songlastskipdate | 10 |
| aspl | daap.songdateplayed | 10 |
| mstc | dmap.utctime | 10 |
| apro | daap.protocolversion | 11 |
| mpro | dmap.protocolversion | 11 |
| abal | daap.browsealbumlisting | 12 |
| abar | daap.browseartistlisting | 12 |
| abcp | daap.browsecomposerlisting | 12 |
| abgn | daap.browsegenrelisting | 12 |
| abro | daap.databasebrowse | 12 |
| adbs | daap.databasesongs | 12 |
| aeAD | com.apple.itunes.adam-ids-array | 12 |
| aply | daap.databaseplaylists | 12 |
| apso | daap.playlistsongs | 12 |
| arif | daap.resolveinfo | 12 |
| arsv | daap.resolve | 12 |
| avdb | daap.serverdatabases | 12 |
| mbcl | dmap.bag | 12 |
| mccr | dmap.contentcodesresponse | 12 |
| mcon | dmap.container | 12 |
| mdcl | dmap.dictionary | 12 |
| mlcl | dmap.listing | 12 |
| mlit | dmap.listingitem | 12 |
| mlog | dmap.loginresponse | 12 |
| msrv | dmap.serverinforesponse | 12 |
| mudl | dmap.deletedidlisting | 12 |
| mupd | dmap.updateresponse | 12 |


Type indicates number of bytes in which codename data must be present

| **Type** | **Details** |
|:---------|:------------|
| 0x0001 | 1 byte integer |
| 0x0002 | 1 unsigned byte integer |
| 0x0003 | 2 byte integer |
| 0x0003 | 2 (unsigned ?) byte integer |
| 0x0005 | 4 byte integer |
| 0x0006 | 4 byte showing seconds (used for dmap.utcoffset) |
| 0x0007 | 8 byte long integer (persistent id, song album id, song size etc) |
| 0x0009 | text string |
| 0x000A | date/timestamp |
| 0x000B | version (protocol version etc.) |
| 0x000C | list/container(can contain other tags) |