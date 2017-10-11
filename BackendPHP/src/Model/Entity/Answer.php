<?php
namespace App\Model\Entity;

use Cake\ORM\Entity;

/**
 * Answer Entity
 *
 * @property string $id
 * @property string $user_id
 * @property string $poll_id
 *
 * @property \App\Model\Entity\User $user
 * @property \App\Model\Entity\Poll $poll
 */
class Answer extends Entity
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
        'user_id' => true,
        'poll_id' => true,
        'user' => true,
        'poll' => true
    ];
}
