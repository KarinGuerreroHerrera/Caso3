public class Mensaje {
    String id;           
    String tipo;     
    boolean esSpam;     
    int tiempoCuarentena; 

    public Mensaje(String id, String tipo, boolean esSpam) {
        this.id = id;
        this.tipo = tipo;
        this.esSpam = esSpam;
        this.tiempoCuarentena = 0;
    }

    public String toString() {
        return "[" + tipo + " " + id + (esSpam ? " (spam)" : "") + "]";
    }
}
