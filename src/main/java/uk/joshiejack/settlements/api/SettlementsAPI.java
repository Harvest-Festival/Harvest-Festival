package uk.joshiejack.settlements.api;

import uk.joshiejack.settlements.api.town.TownAPI;

public class SettlementsAPI {
    public static ISettlementsAPI instance = null;

    public interface ISettlementsAPI {
        /**
         * Returns the Town API instance
         * @return  the town api
         */
        TownAPI towns();
    }
}