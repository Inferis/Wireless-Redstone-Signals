package wrs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.minecraft.util.math.BlockPos;

public class Networks {
    private static HashMap<String, Network> networks = new LinkedHashMap<>();

    public static Set<String> getNetworkNames() {
        return networks.keySet();
    }

    public static void addTransmitter(BlockPos pos, String networkName) {
        var network = networks.get(networkName);
        if (network == null) {
            network = new Network(networkName);
            networks.put(networkName, network);
        }
        network.addTransmitter(pos);
    }

    public static void addReceiver(BlockPos pos, String networkName) {
        var network = networks.get(networkName);
        if (network == null) {
            network = new Network(networkName);
            networks.put(networkName, network);
        }
        network.addReceiver(pos);
        WRS.LOGGER.info("adding receiver " + pos + " from " + networkName);
    }

    public static List<BlockPos> getTransmitters(String networkName) {
        var network = networks.get(networkName);
        if (network != null) {
            return new LinkedList<BlockPos>(network.transmitters);
        }
        else {
            return new LinkedList<BlockPos>();
        }
    }

    public static List<BlockPos> getReceivers(String networkName) {
        var network = networks.get(networkName);
        if (network != null) {
            return new LinkedList<BlockPos>(network.receivers);
        }
        else {
            return new LinkedList<BlockPos>();
        }
    }

    public static void removeTransmitter(BlockPos pos, String networkName) {
        var network = networks.get(networkName);
        if (network != null) {
            network.transmitters.remove(pos);
        }
    }

    public static void removeReceiver(BlockPos pos, String networkName) {
        WRS.LOGGER.info("remove receiver " + pos + " from " + networkName);
        var network = networks.get(networkName);
        if (network != null) {
            network.removeReceiver(pos);
        }
    }

    static class Network {
        String networkName;
        List<BlockPos> transmitters;
        List<BlockPos> receivers;
        
        public Network(String networkName) {
            this.networkName = networkName;
            this.transmitters = new LinkedList<>();
            this.receivers = new LinkedList<>();
        }

        public void addTransmitter(BlockPos pos) {
            if (!transmitters.contains(pos)) {
                transmitters.add(pos);
            }
        }

        public void removeTransmitter(BlockPos pos) {
            if (transmitters.contains(pos)) {
                transmitters.remove(pos);
            }
        }

        public void addReceiver(BlockPos pos) {
            if (!receivers.contains(pos)) {
                receivers.add(pos);
            }
        }

        public void removeReceiver(BlockPos pos) {
            if (receivers.contains(pos)) {
                receivers.remove(pos);
            }
        }
    }
}
