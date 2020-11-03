package ch.cscf.jeci.domain.entities.base;


/**
 * @author: henryp
 */
public enum EntityStatus implements PersistentEnum {

    ACTIVE('A'),
    INACTIVE('N');

    char code;

    EntityStatus(char code){
        this.code=code;
    }

    public char getDbCode(){
        return code;
    }

}
