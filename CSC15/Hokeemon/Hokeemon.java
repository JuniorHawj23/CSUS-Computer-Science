class Hokeemon {
    private String name;
    private String type;
    private int age;

    public Hokeemon(String name, String type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public Hokeemon() {
        this.name = "John";
        this.type = "Dwarf";
        this.age = 15;
    }

    /**
     * @return Location of hokenmon at which he/she lives
     */
    public static String liveIn(Hokeemon hokeemon) {
        switch (hokeemon.getType().toLowerCase()) {
            case "dwarf":
                return "Mountain";
            case "elf":
                return "Dale";
            case "fairy":
                return "Forest";
            case "hobbit":
                return "Shire";
        }
        return "";
    }

    /**
     * @param h Whether current hokemon is friend with h or not
     * @return true if h is friend of current hokemon
     */
    public boolean areFriends(Hokeemon h)
    {
        String lhs = this.type.toLowerCase();
        String rhs = h.type.toLowerCase();

        if(h.getName() == this.getName() ) {
            return false;
        }

        if (lhs.equals(rhs))
            return true;

        if (lhs.equals("dwarf") && rhs.equals("elf") || (rhs.equals("dwarf") && lhs.equals("elf")))
            return true;
        if (lhs.equals("hobbit") && rhs.equals("fairy") || (rhs.equals("hobbit") && lhs.equals("fairy")))
            return true;

        return false;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public int getAge() {
        return this.age;
    }

    public String getEats() {
        return this.getEats();
    }

}
