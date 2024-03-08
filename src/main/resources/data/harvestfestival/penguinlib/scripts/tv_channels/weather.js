/** Called when the tv channel is activated, we then use this and decide
 * which tv program should be played on the tv as well as what the tv chatters
 *
 * WEATHER CHANNEL:
 * The player will find out what the weather forecast is tomorrow
 * We will set the program to whatever the relevant weather condition is
 * We will also then display some random chatter variants for the weather
 *
 * @param {PlayerJS}      player      -   the player interacting with the tv
 * @param {TelevisionJS}  television  -   the television object**/
function watch(player, television) {
    var tomorrow = weather.tomorrow(player.level())
    var snows = player.level().biome(player.pos()).snows(player.pos())
    //var index = calendar.day(player.level()) % 7 + 1
    let index = 1
    if (tomorrow == clear) {
        television.watch('harvestfestival:weather_clear')
        television.chatterTranslated(player, 'tv_program.harvestfestival.weather_clear.' + index)
    } else if (tomorrow == rain) {
        television.watch('harvestfestival:weather_' + (snows ? 'snow' : 'rain'))
        television.chatterTranslated(player, 'tv_program.harvestfestival.weather_' + (snows ? 'snow.' : 'rain.') + index)
    } else if (tomorrow == storm) {
        television.watch('harvestfestival:weather_' + (snows ? 'blizzard' : 'storm'))
        television.chatterTranslated(player, 'tv_program.harvestfestival.weather_' + (snows ? 'blizzard.' : 'storm.'))
    }
}