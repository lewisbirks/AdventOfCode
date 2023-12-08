package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.collection.FrequencyMap;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Day7 extends Day {

    public static void main(String[] args) {
        new Day7().process();
    }

    private List<Hand> hands;

    public Day7() {
        super(7, "Camel Cards");
    }

    @Override
    public void preload() {
        hands = getInput(line -> {
            String[] parts = line.split(" ");
            int bid = Integer.parseInt(parts[1]);
            var hand = new Hand(bid);
            String cards = parts[0];
            for (int i = 0; i < cards.length(); i++) {
                hand.addCard(cards.charAt(i));
            }

            return hand;
        });
    }

    @Override
    public Object part1() {
        List<Hand> hands = new ArrayList<>(this.hands);
        Collections.sort(hands);
        return calculateWinnings(hands);
    }

    private static long calculateWinnings(List<Hand> hands) {
        long sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += ((long) (i + 1) * hands.get(i).getBid());
        }
        return sum;
    }

    @Override
    public Object part2() {
        return null;
    }

    static class Hand implements Comparable<Hand> {

        private static final List<Character> VALID_CARDS =
                List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');

        private final FrequencyMap<Character> cardFrequency;
        private final List<Character> cards;
        private final int bid;
        private HandType handType;

        Hand(int bid) {
            this.bid = bid;
            cardFrequency = new FrequencyMap<>();
            cards = new ArrayList<>();
        }

        public void addCard(char card) {
            if (!VALID_CARDS.contains(card)) {
                throw new IllegalArgumentException("Card " + card + " is not one of " + VALID_CARDS);
            }
            cardFrequency.put(card);
            cards.add(card);
        }

        public int getBid() {
            return bid;
        }

        public HandType getType() {
            if (handType == null) {
                handType = switch (cardFrequency.size()) {
                    case 1 -> HandType.FIVE_OF_A_KIND;
                    case 2 -> cardFrequency.containsValue(4L) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
                    case 3 -> cardFrequency.containsValue(3L) ? HandType.THREE_OF_A_KIND : HandType.TWO_PAIR;
                    case 4 -> HandType.ONE_PAIR;
                    case 5 -> HandType.HIGH_CARD;
                    default -> throw new IllegalStateException("Invalid number of cards " + cardFrequency.size());};
            }
            return handType;
        }

        @Override
        public int compareTo(Hand o) {
            int handComparison = getType().compareTo(o.getType());
            if (handComparison != 0) {
                return handComparison;
            }
            for (int i = 0; i < cards.size(); i++) {
                Character card = cards.get(i);
                Character oCard = o.cards.get(i);
                int power = VALID_CARDS.indexOf(card);
                int oPower = VALID_CARDS.indexOf(oCard);
                int cardComparison = Integer.compare(power, oPower);
                if (cardComparison != 0) {
                    return cardComparison;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Hand{" + "cards=" + cards + ", bid=" + bid + '}';
        }
    }

    enum HandType {
        // order matters it is used for comparison
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }
}
