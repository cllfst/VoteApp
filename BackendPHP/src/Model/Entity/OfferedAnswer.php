<?php
namespace App\Model\Entity;

use Cake\ORM\Entity;

/**
 * OfferedAnswer Entity
 *
 * @property string $id
 * @property string $answer_text
 * @property int $count
 * @property string $question_id
 *
 * @property \App\Model\Entity\Question $question
 */
class OfferedAnswer extends Entity
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
        'answer_text' => true,
        'count' => true,
        'question_id' => true,
        'question' => true
    ];

    function _getPercentage($total) {
        return ($this->count / $total) * 100;
    }
}
