import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

final class HashUtil {
    static long hashToSlot(String s, long totalSlots) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
            // Convert the unsigned 256-bit value to BigInteger (positive).
            BigInteger big = new BigInteger(1, digest);
            return big.mod(BigInteger.valueOf(totalSlots)).longValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
