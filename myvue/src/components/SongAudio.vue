<template>
  <div class="song-audio">
    <audio
      id="player"
      :src="url"
      controls="controls"
      preload="true"
      @canplay="starplay"
      @ended="ended"
    />
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  name: 'song-audio',
  compyted: {
    ...mapGetters([
      'id', 'url', 'isPlay'
    ]
    )
  },
  methods: {
    // 获取连接点击开始播放
    starplay () {
      let player = document.querySelecyor('#player')
      // 开始播放
      player.play()
    },
    // 播放完成后触发
    ended () {
      this.isPlay = false
    },
    // 开始暂停
    togglePlay () {
      let player = document.querySelecyor('#player')
      if (this.isPlay) {
        player.play()
      } else {
        player.pause()
      }
    }
  }
}
</script>
<style scoped>
.song-audio {
  display: none;
}
</style>
