package com.qding.bigdata.ds.util;
 
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
 
/**
 * 类描述:
 *
 * @version 1.0
 * @since 1.0
 */
public class TrackJedisCluster implements FactoryBean<JedisCluster>, InitializingBean {
 
    private String addressConfig;
    private String addressKeyPrefix ;
    private JedisCluster jedisCluster;
    private Integer timeout;
    private Integer maxRedirections;
    private GenericObjectPoolConfig poolConfig;
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
 
    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }
 
    public Class<? extends JedisCluster> getObjectType() {
        return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
    }
 
    public boolean isSingleton() {
        return true;
    }
 
    private Set<HostAndPort> parseHostAndPort() {
        String[] split = addressConfig.split(",");
        // 添加集群的服务节点Set集合
        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
        for(int i=0;i<split.length;i++){
            String[] split1 = split[i].split(":");
            /*String ip=split1[0];
            String port=split1[1];*/
            hostAndPortsSet.add(new HostAndPort(split1[0], Integer.parseInt(split1[1])));
        }
        return hostAndPortsSet;

       /* try {
            Properties prop = new Properties();
            prop.load(this.addressConfig.getInputStream());
 
            Set<HostAndPort> haps = new HashSet<HostAndPort>();
            for (Object key : prop.keySet()) {
 
                if (!((String) key).startsWith(addressKeyPrefix)) {
                    continue;
                }
 
                String val = (String) prop.get(key);
 
                boolean isIpPort = p.matcher(val).matches();
 
                if (!isIpPort) {
                    throw new IllegalArgumentException("ip 或 port 不合法");
                }
                String[] ipAndPort = val.split(":");
 
                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                haps.add(hap);
            }*/
 
       /*     return haps;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;*/
    }
 
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> haps = this.parseHostAndPort();
        jedisCluster = new JedisCluster(haps, timeout, maxRedirections,poolConfig);
    }

    public void setAddressConfig(String addressConfig) {
        this.addressConfig = addressConfig;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public void setAddressKeyPrefix(String addressKeyPrefix) {
        this.addressKeyPrefix = addressKeyPrefix;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}
