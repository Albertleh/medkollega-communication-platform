package at.ac.tuwien.isg.backend.datagenerator;

import java.time.LocalDateTime;
import java.util.List;

public class ChatData {

    public static final List<Message> CONVERSATION_ONE = List.of(
        new Message(
            "Hi, I wanted to update you about our patient. He reported increased difficulty sleeping last week.",
            LocalDateTime.of(2025, 1, 10, 9, 15),
            true
        ),
        new Message(
            "Thanks for the update. During the last session he mentioned persistent worries at night. Did he describe any physical symptoms?",
            LocalDateTime.of(2025, 1, 10, 9, 18),
            true
        ),
        new Message(
            "Yes, mainly tension headaches in the evenings, no other acute symptoms.",
            LocalDateTime.of(2025, 1, 10, 9, 22),
            true
        ),
        new Message(
            "That fits with what he shared in therapy. I’m considering adding a relaxation routine to his treatment plan.",
            LocalDateTime.of(2025, 1, 10, 9, 26),
            true
        ),
        new Message(
            "Sounds good. I’ll also run a basic check-up during his appointment tomorrow, just to rule out anything medical.",
            LocalDateTime.of(2025, 1, 10, 9, 30),
            true
        ),
        new Message(
            "Perfect. Let me know if anything stands out so I can adjust the sessions accordingly.",
            LocalDateTime.of(2025, 1, 10, 9, 34),
            true
        ),
        new Message(
            "Sure thing. By the way, he asked whether increasing physical activity could help. I told him you might address that.",
            LocalDateTime.of(2025, 1, 10, 9, 38),
            true
        ),
        new Message(
            "Yes, I can work on that with him. A structured routine could improve both stress levels and sleep quality.",
            LocalDateTime.of(2025, 1, 10, 9, 42),
            true
        ),
        new Message(
            "Great. After tomorrow’s check-up I’ll send you a summary.",
            LocalDateTime.of(2025, 1, 10, 9, 45),
            true
        ),
        new Message(
            "Thanks, I’ll incorporate it into the therapy plan.",
            LocalDateTime.now().minusHours(5),
            false
        )
    );


    public static final List<Message> CONVERSATION_TWO = List.of(
        new Message(
            "Hi, quick update about our patient: He mentioned having more trouble concentrating during work this week.",
            LocalDateTime.of(2025, 2, 3, 10, 5),
            true
        ),
        new Message(
            "Thanks for letting me know. During our last session he talked about increased mental fatigue. Did he mention any changes in his daily routine?",
            LocalDateTime.of(2025, 2, 3, 10, 9),
            true
        ),
        new Message(
            "Yes, he said he’s been skipping breakfast lately and working longer hours.",
            LocalDateTime.of(2025, 2, 3, 10, 12),
            true
        ),
        new Message(
            "That might be contributing to his concentration issues. I’ll explore his energy levels and habits more next session.",
            LocalDateTime.of(2025, 2, 3, 10, 16),
            true
        ),
        new Message(
            "Good idea. I can also check his vitamin levels during Friday’s appointment, just to rule out deficiencies.",
            LocalDateTime.of(2025, 2, 3, 10, 19),
            true
        ),
        new Message(
            "Perfect, that would help. If anything shows up, we can adapt his coping strategies accordingly.",
            LocalDateTime.of(2025, 2, 3, 10, 23),
            true
        ),
        new Message(
            "Will do. He also asked whether short breaks during work might help him stay focused.",
            LocalDateTime.of(2025, 2, 3, 10, 27),
            true
        ),
        new Message(
            "Yes, I can introduce some pacing techniques and micro-break routines in therapy.",
            LocalDateTime.of(2025, 2, 3, 10, 31),
            true
        ),
        new Message(
            "Great. I’ll send you the results after Friday’s check-up.",
            LocalDateTime.of(2025, 2, 3, 10, 34),
            true
        ),
        new Message(
            "Thanks, I’ll use them to adjust the next session plan.",
            LocalDateTime.now().minusDays(5),
            false
        )
    );

    public static final List<Message> CONVERSATION_THREE = List.of(
        new Message(
            "Hi, I wanted to give you a quick update. The patient reported noticeable mood swings over the past few days.",
            LocalDateTime.of(2025, 3, 14, 11, 10),
            true
        ),
        new Message(
            "Thanks for the info. He mentioned feeling less motivated during our last session. Did he describe any triggers for the mood shifts?",
            LocalDateTime.of(2025, 3, 14, 11, 14),
            true
        ),
        new Message(
            "He said they often happen in the evenings, especially after long meetings at work.",
            LocalDateTime.of(2025, 3, 14, 11, 18),
            true
        ),
        new Message(
            "That aligns with his recent reports about increased work pressure. I’ll focus on stress-management strategies next session.",
            LocalDateTime.of(2025, 3, 14, 11, 22),
            true
        ),
        new Message(
            "Sounds good. I’ll check his blood pressure and run a basic stress panel during his appointment tomorrow.",
            LocalDateTime.of(2025, 3, 14, 11, 26),
            true
        ),
        new Message(
            "Perfect. If anything stands out, let me know so I can adjust his coping techniques and behavioral tasks.",
            LocalDateTime.of(2025, 3, 14, 11, 29),
            true
        ),
        new Message(
            "Will do. He also asked if short daily walks could help stabilize his mood.",
            LocalDateTime.of(2025, 3, 14, 11, 33),
            true
        ),
        new Message(
            "Yes, I can integrate that into his plan. Regular light activity often helps regulate mood patterns.",
            LocalDateTime.of(2025, 3, 14, 11, 37),
            true
        ),
        new Message(
            "Great. I’ll update you after tomorrow’s check-up.",
            LocalDateTime.of(2025, 3, 14, 11, 40),
            true
        ),
        new Message(
            "Hi, I wanted to give you a quick update. The patient reported noticeable mood swings over the past few days.",
            LocalDateTime.now(),
            false
        )
    );



    public record Message(String text, LocalDateTime date, boolean read) {

    }
}
