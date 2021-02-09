package proxy.javaproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MatchMakingTestDrive {

    Map<String, PersonBean> dataBase;

    public static void main(String[] args) {
        MatchMakingTestDrive test = new MatchMakingTestDrive();
        test.drive();
    }

    public MatchMakingTestDrive(){
        initializeDatabase();
    }

    private void initializeDatabase() {
        dataBase = new HashMap<>();
        PersonBean person = new PersonBeanImpl();
        person.setName("Tom");
        person.setGender("male");
        person.setInterests("swim");
        person.setHotOrNotRating(3);
        dataBase.put(person.getName(),person);

        person = new PersonBeanImpl();
        person.setName("Mary");
        person.setGender("female");
        person.setInterests("movies");
        person.setHotOrNotRating(5);
        dataBase.put(person.getName(),person);
    }

    public void drive(){
        PersonBean tom = dataBase.get("Tom");
        PersonBean ownerProxy = getOwnerProxy(tom);
        System.out.println("name is " + ownerProxy.getName());
        ownerProxy.setInterests("bowling");
        System.out.println("Interests set from owner proxy");
        try{
            ownerProxy.setHotOrNotRating(10);
        }catch (Exception e) {
            System.out.println("Can not set rating from owner proxy");
        }
        System.out.println("Rating is" + ownerProxy.getHotOrNotRating());

        PersonBean nonOwnerProxy = getNonOwnerProxy(tom);
        System.out.println("name is " + nonOwnerProxy.getName());
        try{
            nonOwnerProxy.setInterests("bowling");
        }catch (Exception e) {
            System.out.println("Can not set interests from non owner proxy");
        }
        nonOwnerProxy.setHotOrNotRating(3);
        System.out.println("Rating set from non owner proxy");
        System.out.println("Rating is" + nonOwnerProxy.getHotOrNotRating());
    }

    public PersonBean getOwnerProxy(PersonBean person) {

        return (PersonBean) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person)
        );

    }

    public PersonBean getNonOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person)
        );
    }

}
