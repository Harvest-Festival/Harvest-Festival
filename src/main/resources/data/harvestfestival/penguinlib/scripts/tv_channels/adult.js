 var shopping_list = ['frying_pan', 'mixer', 'cooking_pot', 'power_berry']

 /** Called when the tv channel is activated, we then use this and decide
  * which tv program should be played on the tv as well as what the tv chatters
  *
  * COOKING CHANNEL:
  * On this channel the player will be offered a random recipe to be learnt
  * While on Saturdays you will be able to purchase a random utensil and
  * We'll just play some random rubbish on sunday!
  * MONDAY:     RECIPES
  * TUESDAY:    RECIPES
  * WEDNESDAY:  RECIPES
  * THURSDAY:   RECIPES
  * FRIDAY:     RECIPES
  * SATURDAY:   COOKING UTENSILS
  * SUNDAY:     BIG BAKEOFF
  *
  * @param {PlayerJS}      player      -   the player interacting with the tv
  * @param {TelevisionJS}  television  -   the television object**/
 function watch (player, television) {
   television.watch('harvestfestival:adult')
   television.chatterTranslated(player, 'tv_program.harvestfestival.channel.adult.watch')
 }

// function onItemPurchased (player, purchased) {
//   if (purchased.department() == 'cafe_recipes') {
//     var id = purchased.id()
//     if (shopping_list.indexOf(id) > -1) {
//       var npc = settlements.getNearbyNPC(player, 'harvestfestival:liara')
//       if (npc != null) {
//         npc.say(player, 'harvestfestival.television.channel.cooking.' + id + '.ordered')
//       }
//
//       player.status().set(id + '_purchased', 1)
//     }
//   }
// }
