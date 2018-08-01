package shit;

public class Personel implements Comparable{
    
    private long id;
    private String name;
    private String remark;
    private boolean sex;//true->Male false->Female; reasoning:Males>Females in any case==true;
    private int age;
    private String description;
    private String address;
    private String email;
    private String phoneNumber;

    public Personel(long id, String name, boolean sex, int age, String description) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.description = description;
    }

    public Personel(long id, String name, boolean sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public Personel(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Personel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Personel(long id) {
        this.id = id;
    }

    public Personel(long id, String name, String remark, boolean sex, int age, String description, String address, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.sex = sex;
        this.age = age;
        this.description = description;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    //fxxk this, that's too much work! fxxking 256 possibilities!

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public boolean getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o){
        Personel p = (Personel)o;
        return (id==p.getId())||(name.equals(p.getName())&&age==p.getAge()&&sex==p.getSex());
    }
    
    @Override
    public int compareTo(Object o) {
        try{
            Personel otherPersonel = (Personel)o;
            return age-otherPersonel.getAge();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("This object isn't even human!");
            return 100;
        }
    }  
    
}
