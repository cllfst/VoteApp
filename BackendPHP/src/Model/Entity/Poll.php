<?php
namespace App\Model\Entity;

use Cake\ORM\Entity;

/**
 * Poll Entity
 *
 * @property string $id
 * @property string $text
 * @property \Cake\I18n\FrozenTime $start_date
 * @property \Cake\I18n\FrozenTime $end_date
 * @property bool $is_open
 *
 * @property \App\Model\Entity\Answer[] $answers
 * @property \App\Model\Entity\Question[] $questions
 */
class Poll extends Entity
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
        'text' => true,
        'start_date' => true,
        'end_date' => true,
        'is_open' => true,
        'answers' => true,
        'questions' => true
    ];
}
