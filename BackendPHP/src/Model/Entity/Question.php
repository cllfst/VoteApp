<?php
namespace App\Model\Entity;

use Cake\ORM\Entity;

/**
 * Question Entity
 *
 * @property string $id
 * @property string $question_text
 * @property string $poll_id
 *
 * @property \App\Model\Entity\Poll $poll
 * @property \App\Model\Entity\OfferedAnswer[] $offered_answers
 */
class Question extends Entity
{

    /**
     * Fields that can be mass assigned using newEntity() or patchEntity().
     *
     * Note that when '*' is set to true, this allows all unspecified fields to
     * be mass assigned. For security purposes, it is advised to set '*' to false
     * (or remove it), and explicitly make individual fields accessible as needed.
     *
     * @var array
     */
    protected $_accessible = [
        'question_text' => true,
        'poll_id' => true,
        'poll' => true,
        'offered_answers' => true
    ];
}
