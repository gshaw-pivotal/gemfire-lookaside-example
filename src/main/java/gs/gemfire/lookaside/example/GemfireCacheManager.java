package gs.gemfire.lookaside.example;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

public class GemfireCacheManager implements CacheManager{

    private static ClientCache cache;

    private static Region<String, String> region;

    public GemfireCacheManager() {
    }

    public void createCache(String regionName) {
        cache = new ClientCacheFactory()
                .addPoolLocator("127.0.0.1", 10334)
                .set("log-level", "WARN").create();

        region = cache.getRegion(regionName);

        if (region == null) {
            region = cache
                    .<String, String>createClientRegionFactory(ClientRegionShortcut.PROXY)
                    .create(regionName);
        }
    }

    public String getFromRegion(String artist) {
        return region.get(artist);
    }

    public void putIntoRegion(String artist, String songTitle) {
        region.put(artist, songTitle);
    }

    public void close() {
        cache.close();
    }
}
