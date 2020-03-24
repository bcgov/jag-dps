package ca.bc.gov.open.pssg.rsbc.dps.cache;

public interface StorageService {

    String put(byte[] content);

    byte[] get(String key);

}
