package wrs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.util.math.BlockPos;

public class Networks {
    private static HashMap<String, Network> networks = new LinkedHashMap<>();

    public static void registerTransmitter(BlockPos pos, String networkName) {
        var network = networks.get(networkName);
        if (network == null) {
            network = new Network(networkName);
            networks.put(networkName, network);
        }
        network.addTransmitter(pos);
    }

    public static void registerReceiver(BlockPos pos, String networkName) {
        var network = networks.get(networkName);
        if (network == null) {
            network = new Network(networkName);
            networks.put(networkName, network);
        }
        network.addReceiver(pos);
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

    public static void removeTransmitter(String networkName, BlockPos pos) {
        var network = networks.get(networkName);
        if (network != null) {
            network.transmitters.remove(pos);
        }
    }

    public static void removeReceiver(String networkName, BlockPos pos) {
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
                WRS.LOGGER.info("Adding transmitter + " + pos + " to " + networkName);
                transmitters.add(pos);
            }
        }

        public void removeTransmitter(BlockPos pos) {
            if (transmitters.contains(pos)) {
                WRS.LOGGER.info("Removing transmitter + " + pos + " from " + networkName);
                transmitters.remove(pos);
            }
        }

        public void addReceiver(BlockPos pos) {
            if (!receivers.contains(pos)) {
                WRS.LOGGER.info("Adding receiver + " + pos + " to " + networkName);
                receivers.add(pos);
            }
        }

        public void removeReceiver(BlockPos pos) {
            if (receivers.contains(pos)) {
                WRS.LOGGER.info("Removing receiver + " + pos + " from " + networkName);
                receivers.remove(pos);
            }
        }
    }
}
