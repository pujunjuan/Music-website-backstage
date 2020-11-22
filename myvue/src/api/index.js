// eslint-disable-next-line no-unused-vars
import { get, post } from './http'
// ====================歌手相关=================
export const getLoginStatus = (params) => post(`admin/login/status`, params)
// 查询所有歌手
export const SingerAll = () => get(`Singer/selectAll`)
// 添加歌手
export const AddSinger = (params) => post(`Singer/add`, params)
// 编辑歌手
export const EditSinger = (params) => post(`Singer/update`, params)
// 删除歌手
export const delSinger = (id) => get(`Singer/delete?id=${id}`)

// =================歌曲相关==========================
// 查询所有歌曲
export const songAll = () => get(`song/allSong`)
// 歌手id查询歌曲
export const songSingerid = (id) => get(`song/singer/detail?singerId=${id}`)
// 编辑歌曲
export const EditSong = (params) => post(`song/update`, params)
// 删除歌手
export const delSong = (id) => get(`song/delete?id=${id}`)

// 根据歌曲id查询歌单对象
export const songId = (id) => get(`song/detail?songId=${id}`)
// 根据歌曲名查询歌单对象
export const songName = (songName) => get(`song/songOfSongName?songName=${songName}`)

// ====================歌单相关=================

// 查询所有歌单
export const songListAll = () => get(`SongList/selectSongList`)
// 添加歌单
export const AddsongList = (params) => post(`SongList/insert`, params)
// 编辑歌单
export const EditsongList = (params) => post(`SongList/update`, params)
// 删除歌单
export const delsongList = (id) => get(`SongList/detele?id=${id}`)
// 歌单风格查询歌曲
export const songListStyle = (style) => get(`SongList/SongListStyle?style=${style}`)

// ====================歌单的歌曲相关=================
// 歌单id查询列表
export const ListSong = (id) => get(`ListSong/detail?songListId=${id}`)
// 添加歌单的歌曲
export const AddListSong = (params) => post(`ListSong/add`, params)
// 删除歌单
export const delListSong = (songId, songListId) => get(`/ListSong/delete?songId=${songId}&songListId=${songListId}`)

// ====================用户相关=================
// 用户全部查询
export const UserListAll = () => get(`User/selectAll`)
// 用户添加
export const AddUser = (params) => post(`User/add`, params)
// 用户修改
export const EditUser = (params) => post(`User/update`, params)
// 用户删除
export const delUser = (id) => get(`User/delete?id=${id}`)
