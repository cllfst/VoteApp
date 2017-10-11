<?php
/**
 * @var \App\View\AppView $this
 * @var \App\Model\Entity\Poll $poll
 */
?>
<nav class="large-3 medium-4 columns" id="actions-sidebar">
    <ul class="side-nav">
        <li class="heading"><?= __('Actions') ?></li>
        <li><?= $this->Html->link(__('Edit Poll'), ['action' => 'edit', $poll->id]) ?> </li>
        <li><?= $this->Form->postLink(__('Delete Poll'), ['action' => 'delete', $poll->id], ['confirm' => __('Are you sure you want to delete # {0}?', $poll->id)]) ?> </li>
        <li><?= $this->Html->link(__('List Polls'), ['action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Poll'), ['action' => 'add']) ?> </li>
        <li><?= $this->Html->link(__('List Answers'), ['controller' => 'Answers', 'action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Answer'), ['controller' => 'Answers', 'action' => 'add']) ?> </li>
        <li><?= $this->Html->link(__('List Questions'), ['controller' => 'Questions', 'action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Question'), ['controller' => 'Questions', 'action' => 'add']) ?> </li>
    </ul>
</nav>
<div class="polls view large-9 medium-8 columns content">
    <h3><?= h($poll->id) ?></h3>
    <table class="vertical-table">
        <tr>
            <th scope="row"><?= __('Id') ?></th>
            <td><?= h($poll->id) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Text') ?></th>
            <td><?= h($poll->text) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Start Date') ?></th>
            <td><?= h($poll->start_date) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('End Date') ?></th>
            <td><?= h($poll->end_date) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Is Open') ?></th>
            <td><?= $poll->is_open ? __('Yes') : __('No'); ?></td>
        </tr>
    </table>
    <div class="related">
        <h4><?= __('Related Answers') ?></h4>
        <?php if (!empty($poll->answers)): ?>
        <table cellpadding="0" cellspacing="0">
            <tr>
                <th scope="col"><?= __('User Id') ?></th>
                <th scope="col"><?= __('Poll Id') ?></th>
                <th scope="col" class="actions"><?= __('Actions') ?></th>
            </tr>
            <?php foreach ($poll->answers as $answers): ?>
            <tr>
                <td><?= h($answers->user_id) ?></td>
                <td><?= h($answers->poll_id) ?></td>
                <td class="actions">
                    <?= $this->Html->link(__('View'), ['controller' => 'Answers', 'action' => 'view', $answers->user_id]) ?>
                    <?= $this->Html->link(__('Edit'), ['controller' => 'Answers', 'action' => 'edit', $answers->user_id]) ?>
                    <?= $this->Form->postLink(__('Delete'), ['controller' => 'Answers', 'action' => 'delete', $answers->user_id], ['confirm' => __('Are you sure you want to delete # {0}?', $answers->user_id)]) ?>
                </td>
            </tr>
            <?php endforeach; ?>
        </table>
        <?php endif; ?>
    </div>
    <div class="related">
        <h4><?= __('Related Questions') ?></h4>
        <?php if (!empty($poll->questions)): ?>
        <table cellpadding="0" cellspacing="0">
            <tr>
                <th scope="col"><?= __('Id') ?></th>
                <th scope="col"><?= __('Question Text') ?></th>
                <th scope="col"><?= __('Poll Id') ?></th>
                <th scope="col" class="actions"><?= __('Actions') ?></th>
            </tr>
            <?php foreach ($poll->questions as $questions): ?>
            <tr>
                <td><?= h($questions->id) ?></td>
                <td><?= h($questions->question_text) ?></td>
                <td><?= h($questions->poll_id) ?></td>
                <td class="actions">
                    <?= $this->Html->link(__('View'), ['controller' => 'Questions', 'action' => 'view', $questions->id]) ?>
                    <?= $this->Html->link(__('Edit'), ['controller' => 'Questions', 'action' => 'edit', $questions->id]) ?>
                    <?= $this->Form->postLink(__('Delete'), ['controller' => 'Questions', 'action' => 'delete', $questions->id], ['confirm' => __('Are you sure you want to delete # {0}?', $questions->id)]) ?>
                </td>
            </tr>
            <?php endforeach; ?>
        </table>
        <?php endif; ?>
    </div>
</div>
