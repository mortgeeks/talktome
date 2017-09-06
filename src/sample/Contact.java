package sample;

class Contact{
    private String Name;
    private int Id;
    public void Contact(String name,int id){
        this.Name = name;
        this.Id=id;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }
}