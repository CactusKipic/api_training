package fr.esiea.ex4A.mockmeet.tools;

import fr.esiea.ex4A.mockmeet.json.User;
import fr.esiea.ex4A.mockmeet.json.UserAgify;

import java.util.*;
import java.util.stream.Collectors;

public class UserStocks {
    
    private static HashMap<String, UserAgify> nameToAUser = new HashMap<>();
    private static HashMap<Integer, ArrayList<UserAgify>> ageToAUser = new HashMap<>();
    private static HashMap<UserAgify, ArrayList<User>> nameToUser = new HashMap<>();
    
    public static void addUser(UserAgify userAgify, User user){
        // Association d'un UserAgify avec id permettant de le retrouver facilement.
        // Agify renvoyant le même résultat pour un même nom et pays, il ne peut y avoir de collision
        if(!nameToAUser.containsKey(makeUserID(userAgify)))
            nameToAUser.put(makeUserID(userAgify), userAgify);
        
        // Association d'un UserAgify avec un âge. Création d'une liste si vide, ou ajout à l'existante
        if (!ageToAUser.containsKey(userAgify.age)) {
            ageToAUser.put(userAgify.age, new ArrayList<>(Collections.singletonList(userAgify)));
        } else
            if(!ageToAUser.get(userAgify.age).contains(userAgify))
                ageToAUser.get(userAgify.age).add(userAgify);
        
        // Association d'un User avec un UserAgify. Création d'une liste si vide, ou ajout à l'existante
        if (!nameToUser.containsKey(userAgify)) {
            nameToUser.put(userAgify, new ArrayList<>(Collections.singletonList(user)));
        } else
            nameToUser.get(userAgify).add(user);
    }
    
    // Retourne la liste de tous les Users associé à cette âge par Agify
    public static List<List<User>> getUsersOfAge(int age){
        List<UserAgify> agifyUsersOfAge = getAgifyUsersOfAge(age);
        return agifyUsersOfAge == null ? null : agifyUsersOfAge.stream().map(UserStocks::getUsersOfAgifyUser).collect(Collectors.toList());
    }
    
    // Renvoie les UserAgify associé à cette âge par Agify
    public static List<UserAgify> getAgifyUsersOfAge(int age){
        return ageToAUser.get(age);
    }
    
    // Retourne la liste des Users associé à un même résultat Agify
    public static List<User> getUsersOfAgifyUser(UserAgify userAgify){
        return nameToUser.get(userAgify);
    }
    
    // Retourne le UserAgify associé au User en entrée
    public static UserAgify getUserAgify(User user){
        return nameToAUser.get(makeUserID(user));
    }
    
    // Retourne le UserAgify associé au User en entrée
    public static UserAgify getUserAgify(String name, String contry_id){
        return nameToAUser.get(name.concat("_").concat(contry_id));
    }
    
    // Check if this user has already a corresponding UserAgify in cache
    public static boolean userAlreadyExist(User user){
        return nameToAUser.containsKey(makeUserID(user));
    }
    
    // Associe le nom d'un User à son Pays, s'assurant qu'il n'y ait pas de doublon dans la HashMap
    // ainsi que permettant de facilement retrouver cet ID en suite
    private static String makeUserID(UserAgify userAgify){
        return userAgify.name.concat("_").concat(userAgify.country_id);
    }
    
    // Associe le nom d'un User à son Pays, s'assurant qu'il n'y ait pas de doublon dans la HashMap
    // ainsi que permettant de facilement retrouver cet ID en suite
    private static String makeUserID(User user){
        return user.userName.concat("_").concat(user.userCountry);
    }
    
}
