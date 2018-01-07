package com.rajatv.surajv.roshank.sac.DataStore;

/**
 * Created by CREATOR on 11/3/2017.
 */

public class DataStore {


    public static String getData(String data){
        String datastring = null;
        switch (data){
            case "Aam":
                return "Are you creative enough to bring your dreams to life, to visualise your ideas and give them a shape? Yes! Then this the right place for you to unveil your talent.\n"
                        +"Creativity is a wild mind and a disciplined eye. It is an art to explore your ideas to extraordinary way from the ordinary work.”</i>\n"
                        +"Dare to expand your imagination to visualize a landscape to blend man’s technology into nature’s surroundings or design a building that could idealize your thoughts. Have an eye to catch the amazing moments and a mind to turn a waste free world with the following events.\n";

            case "Be World":
                return "Explore your mind, code your thoughts, compile your ideas and give your passion a run !\n" +
                        "Game your way in and unleash the esoteric potential in you !\n" +
                        "A true gamer knows that the secret of getting ahead is to get started. So what to wait for, shout out your passion to the world  with these exciting events.\n" ;

            case "OM":
                return "You must have struggled a lot with the famous ohm’s law, faraday’s law, Kirchhoff’s law, Coulomb’s law many more.\n" +
                        "Have you ever dreamt of being bombarded by a stream of resistors, capacitors, diodes, inductors??\n" +
                        "It’s time to flick the switch of your great mind and expanding the horizons towards the bright spark.</i>\n" +
                        "So, if you think you’re the king of circuits then, get ready for the exciting events of TCF’17.\n" +
                        "Get electrified and not shocked at the unshockables.";

            case "Ccrete":
                return "Concrete is a building material made from a mixture of broken stones or gravel, sand, cement and water which can be spread or poured into moulds and forms a stone like mass on hardening.\n" +
                        "Do you have those skills to rise the building of your knowledge with a strong mixture of creative materials and form the best revolutionary structures.\n" +
                        "So get ready for interesting events of TCF’17  to show that your concrete mix is the best satisfying all constaints.\n" ;

            case "Rotics":
                return "Robotics is about us. It is the discipline of emulating our lives, of wondering how we work.\n" +
                        "If you believe the same and have a fantasy to build robots to serve your commands in a go and if you feel that ROBOTS ARE THE FUTURE ! Then get ready to design the Robot you have always wanted in the most exciting event of TCF’17- Robotics.\n" +
                        "Explore, examine and analyse the boundaries of present and get an insight into the future. Integrate your ideas and skills to build the machines that imitates humans. Dare to be the creator !\n";
        }
        return datastring;
    }
}
