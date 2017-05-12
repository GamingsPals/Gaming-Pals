package utilities;


import domain.Message;
import domain.Team;
import domain.User;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class RelatedUsers {

    private User principal;
    private Map<User,Double> relatedUsers;


    public RelatedUsers(User principal){
        this.principal = principal;
        this.relatedUsers = new HashMap<>();
    }

    public List<User> getRelatedUsers(){
        Map<User,Double> map = formatMap();
        List<User> result = new ArrayList<>(map.keySet());
        result.remove(principal);
        return result.subList(0,Math.min(5,result.size()));
    }

    private Map<User,Double> formatMap(){
        return this.relatedUsers.entrySet()
                .stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public void calculate(){
        byTeams();
        byMessages();
        byFollowAndFollower();
    }

    private void byFollowAndFollower() {
        User u= principal;
        for(User us: u.getFollowerUsers()){
            Double value = 0.;
            if(relatedUsers.containsKey(us)){
                value = relatedUsers.get(us);
            }
            value+=1;
            relatedUsers.put(us,value);
        }
        for(User us: u.getFollowingUsers()){
            Double value = 0.;
            if(relatedUsers.containsKey(us)){
                value = relatedUsers.get(us);
            }
            value+=1;
            relatedUsers.put(us,value);
        }
    }

    private void byMessages() {
        User u = principal;
        for(Message m: u.getSended()){
            if(!(m.getReceiver() instanceof User)){
                continue;
            }
            User us = (User) m.getReceiver();
            Double value = 0.;
            if(relatedUsers.containsKey(us)){
                value = relatedUsers.get(us);
            }
            value+=0.2;
            relatedUsers.put(us,value);
        }
        for(Message m: u.getReceived()){
            if(!(m.getSender() instanceof User)){
                continue;
            }
            User us = (User) m.getSender();
            Double value = 0.;
            if(relatedUsers.containsKey(us)){
                value = relatedUsers.get(us);
            }
            value+=0.1;
            relatedUsers.put(us,value);
        }
    }


    private void byTeams(){
        User u = principal;
        Assert.notNull(u);
        List<Team> teams = new ArrayList<>( u.getTeams());
        for(Team t : teams){
            for(User e: t.getUsers()){
                if(e.equals(u)) continue;
                Double value = 0.;
                if(relatedUsers.containsKey(e)){
                    value = relatedUsers.get(e);
                }
                value+=1.;
                relatedUsers.put(e,value);
            }
        }
    }


}
