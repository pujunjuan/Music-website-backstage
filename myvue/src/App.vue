<template>
  <div id="app">
    <Song-audio></Song-audio>
    <router-view />
  </div>
</template>

<script>
import SongAudio from './components/SongAudio'
export default {
  name: 'App',
  components: {
    SongAudio
  }
}
</script>

<style>
</style>
