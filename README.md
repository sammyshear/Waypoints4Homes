# Waypoints for Homes

![Modrinth Downloads](https://img.shields.io/modrinth/dt/waypoints-for-homes?style=for-the-badge&logo=modrinth&label=Downloads)

[![Publish to Modrinth](https://github.com/sammyshear/Waypoints4Homes/actions/workflows/modrinth.yml/badge.svg)](https://github.com/sammyshear/Waypoints4Homes/actions/workflows/modrinth.yml)
[![Release on Github](https://github.com/sammyshear/Waypoints4Homes/actions/workflows/github-release.yml/badge.svg)](https://github.com/sammyshear/Waypoints4Homes/actions/workflows/github-release.yml)

[![Static Badge](https://img.shields.io/badge/Kofi-Support%20me?style=flat&logo=kofi&color=blue&link=https%3A%2F%2Fko-fi.com%2Fsammyshear)](https://ko-fi.com/sammyshear)


This is a mod/plugin combination that automatically creates and updates Xaero's Minimap or JourneyMap waypoints from EssentialsX or HuskHomes homes.


## Install
Install the mod client side with the fabric mod loader, and install the plugin on a Paper server. This is required for the mod to work as otherwise it will not receive any packets. If you don't have the ability to add this plugin to the server you want to use this mod on, it will not work. I do not know of any way to fix that.
### Important Note
If you're installing from Github releases make sure to download the right jar, you want the ones that don't say dev or source.
## Why?
I don't know if anyone else has actually thought this should be a mod before, but I was bothered by having to set waypoints manually for each home I set on vanilla servers, so I made this mod/plugin for my personal use mainly, but since I was hoping this already existed in some form before I went ahead and made it, I decided to make it open source and release it.

## Building from Scratch
There are two ways to do this because of the way I set up the gradle project, you can either run the `build` task for whichever subproject you'd like to build (i.e. `./gradlew :modules:bukkit:build`), and use the release jar (not the one marked dev or sources), or you can run either the main project's `build` task or `buildAll` a task I made before I had fully figured out the workflow I wanted for the project.

## Contributing
Because of the simple nature of the project, I don't expect to update it much unless I encounter bugs or any are reported to me, but if you want to add any functionality, feel free to submit a pull request with your changes. This could range from adjusting the way the color of the waypoint is decided to adding functionality for a completely different homes plugin. If I think it's in the purview of the mod and think it would be a useful addition, I'm happy to merge it.
