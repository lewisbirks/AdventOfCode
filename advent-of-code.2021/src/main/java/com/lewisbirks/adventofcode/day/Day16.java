package com.lewisbirks.adventofcode.day;

import static com.lewisbirks.adventofcode.utils.StringUtils.binaryToInt;
import static com.lewisbirks.adventofcode.utils.StringUtils.binaryToLong;
import static com.lewisbirks.adventofcode.utils.StringUtils.hexToBinary;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Day16 extends Day {

    private static final int LITERAL_SIZE = 5;
    private static final char LITERAL_TERMINATION = '0';
    private static final int LENGTH_TYPE_ID_NUM_PACKETS_BITS = 11;
    private static final int LENGTH_TYPE_ID_TOTAL_LENGTH_BITS = 15;

    private String packets;
    private int subPacketIndex;

    public Day16() {
        super(16, "Packet Decoder");
    }

    @Override
    public void preload() {
        packets = hexToBinary(readInput().trim());
    }

    @Override
    public Object part1() {
        return sumPacketVersions(packets);
    }

    @Override
    public Object part2() {
        subPacketIndex = 0;
        // top packet should be an operator so should only have one value
        return evaluatePacket(packets, Integer.MAX_VALUE).get(0);
    }

    private int sumPacketVersions(final String packets) {
        int sum = 0;
        int index = 0;
        while (index < packets.length() && stillHasContent(packets, index)) {
            int version = binaryToInt(packets.substring(index, index + 3));
            sum += version;
            index += 3;
            int typeID = binaryToInt(packets.substring(index, index + 3));
            index += 3;

            if (typeID == 4) {
                // literal
                do {
                    index += LITERAL_SIZE;
                } while (packets.charAt(index - LITERAL_SIZE) != LITERAL_TERMINATION);
            } else {
                // operator
                char lengthTypeId = packets.charAt(index);
                int subPacketsLengthNumBits =
                        lengthTypeId == '0' ? LENGTH_TYPE_ID_TOTAL_LENGTH_BITS : LENGTH_TYPE_ID_NUM_PACKETS_BITS;
                index++;
                int subPacketsLength = binaryToInt(packets.substring(index, index + subPacketsLengthNumBits));
                index += subPacketsLengthNumBits;
                // need to check the substring if only the length is specified
                // otherwise the loop will then check the next sub packet
                if (lengthTypeId == '0') {
                    sum += sumPacketVersions(packets.substring(index, index + subPacketsLength));
                    index += subPacketsLength;
                }
            }
        }
        return sum;
    }

    private List<Long> evaluatePacket(final String packets, int numToParse) {
        int index = 0;
        int parsedPackets = 0;
        List<Long> values = new ArrayList<>();
        while (index < packets.length() && parsedPackets < numToParse && stillHasContent(packets, index)) {
            int typeID = binaryToInt(packets.substring(index + 3, index + 6));
            index += 6;

            if (typeID == 4) {
                // literal
                StringBuilder literalValue = new StringBuilder();
                do {
                    literalValue.append(packets, index + 1, index + LITERAL_SIZE);
                    index += LITERAL_SIZE;
                } while (packets.charAt(index - LITERAL_SIZE) != LITERAL_TERMINATION);
                values.add(binaryToLong(literalValue.toString()));
            } else {
                // operator
                char lengthTypeId = packets.charAt(index);
                boolean definesLength = lengthTypeId == '0';
                int subPacketsLengthNumBits =
                        definesLength ? LENGTH_TYPE_ID_TOTAL_LENGTH_BITS : LENGTH_TYPE_ID_NUM_PACKETS_BITS;
                index++;
                int subPacketsData = binaryToInt(packets.substring(index, index + subPacketsLengthNumBits));
                index += subPacketsLengthNumBits;

                int subStringEndIndex = definesLength ? index + subPacketsData : packets.length();
                int packetsToParse = definesLength ? Integer.MAX_VALUE : subPacketsData;
                values.add(performOperation(
                        evaluatePacket(packets.substring(index, subStringEndIndex), packetsToParse), typeID));
                index += definesLength ? subPacketsData : subPacketIndex;
            }
            subPacketIndex = index;
            parsedPackets++;
        }
        return values;
    }

    private long performOperation(List<Long> values, int operationCode) {
        if (values.isEmpty()) {
            throw new IllegalStateException("List should not be empty");
        }
        return switch (operationCode) {
            case 0 -> values.stream().mapToLong(Long::longValue).sum();
            case 1 -> values.stream()
                    .mapToLong(Long::longValue)
                    .reduce((i, j) -> i * j)
                    .getAsLong();
            case 2 -> values.stream().mapToLong(Long::longValue).min().getAsLong();
            case 3 -> values.stream().mapToLong(Long::longValue).max().getAsLong();
            case 5 -> values.get(0) > values.get(1) ? 1 : 0;
            case 6 -> values.get(0) < values.get(1) ? 1 : 0;
            case 7 -> Objects.equals(values.get(0), values.get(1)) ? 1 : 0;
            default -> throw new IllegalStateException("Unexpected operation code: " + operationCode);
        };
    }

    private boolean stillHasContent(String packets, int index) {
        return packets.substring(index).chars().anyMatch(c -> c != '0');
    }
}
