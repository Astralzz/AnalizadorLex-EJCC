package analizador_2;

public class TokenPersonalizado {

   public String _token;
  public  String _lexema;

    public TokenPersonalizado(String lexema, String token) {
        this._lexema = lexema;
        this._token = token;
    }

    public String getLexema() {
        return this._lexema;
    }

    public String getToken() {
        return this._token;
    }

    public String toString() {
        return "Lexema: " + this._lexema + " Token: " + this._token + ";";
    }

}
