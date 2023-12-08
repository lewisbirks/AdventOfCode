package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.collection.FrequencyMap;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Comparator;
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
        hands.sort(Hand.STANDARD_CARD_COMPARATOR);
        return calculateWinnings(hands);
    }

    private static long calculateWinnings(List<Hand> hands) {
        long sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += hands.get(i).getWinnings(i + 1);
        }
        return sum;
    }

    @Override
    public Object part2() {
        List<Hand> hands = new ArrayList<>(this.hands);
        hands.sort(Hand.JOKER_CARD_COMPARATOR);
        return calculateWinnings(hands);
    }

    static class Hand {

        private static final List<Character> VALID_CARDS =
                List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
        private static final List<Character> JOKER_CARD_RANKINGS =
                List.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');

        public static final Comparator<Hand> STANDARD_CARD_COMPARATOR = comparator(VALID_CARDS, false);
        public static final Comparator<Hand> JOKER_CARD_COMPARATOR = comparator(JOKER_CARD_RANKINGS, true);

        private final FrequencyMap<Character> cardFrequency;
        private final List<Character> cards;
        private final int bid;
        private HandType type;

        public Hand(int bid) {
            this.bid = bid;
            cardFrequency = new FrequencyMap<>();
            cards = new ArrayList<>();
        }

        public void addCard(Character card) {
            if (!VALID_CARDS.contains(card)) {
                throw new IllegalArgumentException("Card " + card + " is not one of " + VALID_CARDS);
            }
            cardFrequency.put(card);
            cards.add(card);
        }

        public long getWinnings(int multiplier) {
            return (long) multiplier * bid;
        }

        public HandType getType(boolean considerJokers) {
            if (type != null) {
                return type;
            }

            type = switch (cardFrequency.size()) {
                case 1 -> HandType.FIVE_OF_A_KIND;
                case 2 -> cardFrequency.containsValue(4L) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
                case 3 -> cardFrequency.containsValue(3L) ? HandType.THREE_OF_A_KIND : HandType.TWO_PAIR;
                case 4 -> HandType.ONE_PAIR;
                case 5 -> HandType.HIGH_CARD;
                default -> throw new IllegalStateException("Invalid number of cards " + cardFrequency.size());};

            if (!considerJokers || !cards.contains('J')) {
                return type;
            }

            type = switch (type) {
                    // ABCDJ -> ABCDD
                case HIGH_CARD -> HandType.ONE_PAIR;
                    // AAJCD -> AAACD
                    // JJBCD -> BBBCD (same as above)
                case ONE_PAIR -> HandType.THREE_OF_A_KIND;
                    // AABBJ -> AABBB
                    // JJBBC -> BBBBC
                case TWO_PAIR -> cardFrequency.get('J') == 1 ? HandType.FULL_HOUSE : HandType.FOUR_OF_A_KIND;
                    // AAAJC -> AAAAC
                    // JJJBC -> BBBBC (same as above)
                case THREE_OF_A_KIND -> HandType.FOUR_OF_A_KIND;
                case FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND -> HandType.FIVE_OF_A_KIND;};

            return type;
        }

        private static Comparator<Hand> comparator(List<Character> powerRanking, boolean considerJokers) {
            return (hand, other) -> {
                int handComparison = hand.getType(considerJokers).compareTo(other.getType(considerJokers));
                if (handComparison != 0) {
                    return handComparison;
                }
                int size = hand.cards.size();
                for (int i = 0; i < size; i++) {
                    int power = powerRanking.indexOf(hand.cards.get(i));
                    int oPower = powerRanking.indexOf(other.cards.get(i));
                    int cardComparison = Integer.compare(power, oPower);
                    if (cardComparison != 0) {
                        return cardComparison;
                    }
                }
                return 0;
            };
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
