package nl.infosys.hartigehap.supplySystem.businesslogic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import nl.infosys.hartigehap.supplySystem.databaseacces.PurchaseDao;
import nl.infosys.hartigehap.supplySystem.databaseacces.SupplierDao;
import nl.infosys.hartigehap.supplySystem.domain.Interface_IngredientReadOnly;
import nl.infosys.hartigehap.supplySystem.domain.Interface_PurchaseOrderReadOnly;
import nl.infosys.hartigehap.supplySystem.domain.Interface_SupplierReadOnly;
import nl.infosys.hartigehap.supplySystem.domain.PurchaseOrder;
import nl.infosys.hartigehap.supplySystem.domain.PurchaseOrderLine;
import nl.infosys.hartigehap.supplySystem.domain.Supplier;

/**
 *
 * @author Gregor
 */
public class PurchaseManager {

    private IngredientManager ingredientManager;
    private TreeMap<Integer, PurchaseOrder> orderList;
    private Map<Integer, Supplier> supplierList;

    /**
     * The possible statusses the order can have.
     */
    public static enum Status {

        ORDERED("Besteld"),
        DELIVERD("Geleverd"),
        OPEN("Offerte"),
        CANCELED("Afgewezen");

        private String presentationString;

        private Status(String presentationString) {
            this.presentationString = presentationString;

        }

        public String getPresentationString() {
            return presentationString;
        }
    };

    /**
     *
     * @param ingredientManager
     */
    public PurchaseManager(IngredientManager ingredientManager) {
        this.ingredientManager = ingredientManager;
        loadAllSuppliers();
        loadAllOrders();
    }

    /**
     *
     * @param page
     */
    private void loadAllOrders() {
        PurchaseDao purchaseOrderDAO = new PurchaseDao();
        orderList = new TreeMap(purchaseOrderDAO.getPurchaseOrders(supplierList, ingredientManager));
    }

    /**
     *
     */
    private void loadAllSuppliers() {
        SupplierDao supplierDAO = new SupplierDao();
        supplierList = new LinkedHashMap<>(supplierDAO.getSuppliers());
    }

    /**
     *
     * @return
     */
    public List<Interface_PurchaseOrderReadOnly> getPurchaseOrders() {
        List<PurchaseOrder> list = new ArrayList(orderList.values());
        List<Interface_PurchaseOrderReadOnly> returnList = new ArrayList<>();

        for (PurchaseOrder order : list) {
            if (order.getStatus() == Status.ORDERED || order.getStatus() == Status.DELIVERD) {
                returnList.add(order);
            }
        }

        return returnList;
    }

    /**
     *
     * @return
     */
    public List<Interface_PurchaseOrderReadOnly> getPurchaseQuotations() {
        List<PurchaseOrder> list = new ArrayList(orderList.values());
        List<Interface_PurchaseOrderReadOnly> returnList = new ArrayList<>();

        for (PurchaseOrder order : list) {
            if (order.getStatus() == Status.OPEN || order.getStatus() == Status.CANCELED) {
                returnList.add(order);
            }
        }

        return returnList;
    }

    /**
     *
     * @param id
     * @return
     */
    public Interface_PurchaseOrderReadOnly getOrder(int id) {
        return orderList.get(id);
    }

    /**
     *
     * @return
     */
    public int createNewOrder() {
        int id = orderList.lastKey() + 1;
        orderList.put(id, new PurchaseOrder(id, (System.currentTimeMillis() / 1000), Status.ORDERED));

        return id;
    }

    /**
     *
     * @return
     */
    public int createNewQuotation() {
        int id = orderList.lastKey() + 1;
        orderList.put(id, new PurchaseOrder(id, (System.currentTimeMillis() / 1000), Status.OPEN));

        return id;
    }

    public void addOrderLine(int orderId, Interface_IngredientReadOnly ingredient, int amount, double price) {
        orderList.get(orderId).addOrderLine(ingredient, amount, price);
    }

    public void replaceOrderLine(int orderId, int lineIndex, Interface_IngredientReadOnly ingredient, int amount, double price) {
        orderList.get(orderId).replaceOrderline(lineIndex, ingredient, amount, price);
    }

    public void deleteOrderLine(int orderId, int lineIndex) {
        orderList.get(orderId).removeOrderLine(lineIndex);
    }

    /**
     *
     * @param id
     */
    public void deleteOrder(int id) {
        orderList.remove(id);
    }

    /**
     *
     * @return
     */
    public List<Interface_SupplierReadOnly> getSuppliers() {
        return new ArrayList(supplierList.values());
    }

    /**
     *
     * @param id
     * @return
     */
    public Interface_SupplierReadOnly getSupplier(int id) {
        return supplierList.get(id);
    }

    /**
     *
     * @param orderId
     * @param supplierId
     */
    public void setSupplier(int orderId, int supplierId) {
        orderList.get(orderId).setSupplier(supplierList.get(supplierId));
    }

    /**
     *
     * @param id
     */
    public void saveOrder(int id) {
        PurchaseDao purchaseOrderDAO = new PurchaseDao();
        purchaseOrderDAO.saveOrder(orderList.get(id));
    }

    /**
     *
     * @param id
     * @param stringStatus
     */
    public void changeStatus(int id, String stringStatus) {
        PurchaseOrder order = orderList.get(id);
        switch (stringStatus) {
            case "Offerte":
                order.setStatus(Status.OPEN);
                break;
            case "Afgewezen":
                order.setStatus(Status.CANCELED);
                break;
            case "Besteld":
                order.setStatus(Status.ORDERED);
                break;
            case "Geleverd":
                order.setStatus(Status.DELIVERD);
                updateStock(id);
                break;
        }
    }

    /**
     *
     */
    public void refreshOrders() {
        loadAllOrders();
    }

    /**
     *
     */
    public void refreshSuppliers() {
        loadAllSuppliers();
    }

    public Status[] getNewStatus(Status originalStatus) {
        switch (originalStatus) {
            case OPEN:
                return new Status[]{
                    Status.CANCELED, Status.ORDERED
                };
            case ORDERED:
                return new Status[]{
                    Status.DELIVERD
                };
            case CANCELED:
            case DELIVERD:
            default:
                return null;
        }
    }

    private void updateStock(int id) {
        for (PurchaseOrderLine orderline : orderList.get(id).getOrderlines()) {
            ingredientManager.updateStock(orderline.getIngredient().getId(), (ingredientManager.getIngredient(orderline.getIngredient().getId()).getStock() + orderline.getAmount()));
            ingredientManager.saveIngredient(orderline.getIngredient().getId());
        }
    }
}
