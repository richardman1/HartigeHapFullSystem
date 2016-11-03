package nl.infosys.hartigehap.supplySystem.domain;

/**
 *
 * @author Gregor
 */
public class Supplier implements Interface_SupplierReadOnly {

    String name, address;
    int id;

    /**
     *
     * @param name
     * @param address
     * @param id
     */
    public Supplier(String name, String address, int id) {
        this.name = name;
        this.address = address;
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
