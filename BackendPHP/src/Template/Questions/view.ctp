<?php
/**
 * @var \App\View\AppView $this
 * @var \App\Model\Entity\Question $question
 */
?>
<nav class="large-3 medium-4 columns" id="actions-sidebar">
    <ul class="side-nav">
        <li class="heading"><?= __('Actions') ?></li>
        <li><?= $this->Html->link(__('Edit Question'), ['action' => 'edit', $question->id]) ?> </li>
        <li><?= $this->Form->postLink(__('Delete Question'), ['action' => 'delete', $question->id], ['confirm' => __('Are you sure you want to delete # {0}?', $question->id)]) ?> </li>
        <li><?= $this->Html->link(__('List Questions'), ['action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Question'), ['action' => 'add']) ?> </li>
        <li><?= $this->Html->link(__('List Polls'), ['controller' => 'Polls', 'action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Poll'), ['controller' => 'Polls', 'action' => 'add']) ?> </li>
        <li><?= $this->Html->link(__('List Offered Answers'), ['controller' => 'OfferedAnswers', 'action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Offered Answer'), ['controller' => 'OfferedAnswers', 'action' => 'add']) ?> </li>
    </ul>
</nav>
<div class="questions view large-9 medium-8 columns content">
    <h3><?= h($question->id) ?></h3>
    <table class="vertical-table">
        <tr>
            <th scope="row"><?= __('Id') ?></th>
            <td><?= h($question->id) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Question Text') ?></th>
            <td><?= h($question->question_text) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Poll') ?></th>
            <td><?= $question->has('poll') ? $this->Html->link($question->poll->id, ['controller' => 'Polls', 'action' => 'view', $question->poll->id]) : '' ?></td>
        </tr>
    </table>
    <div class="related">
        <h4><?= __('Related Offered Answers') ?></h4>
        <?php if (!empty($question->offered_answers)): ?>
        <table cellpadding="0" cellspacing="0">
            <tr>
                <th scope="col"><?= __('Id') ?></th>
                <th scope="col"><?= __('Answer Text') ?></th>
                <th scope="col"><?= __('Count') ?></th>
                <th scope="col"><?= __('Question Id') ?></th>
                <th scope="col" class="actions"><?= __('Actions') ?></th>
            </tr>
            <?php foreach ($question->offered_answers as $offeredAnswers): ?>
            <tr>
                <td><?= h($offeredAnswers->id) ?></td>
                <td><?= h($offeredAnswers->answer_text) ?></td>
                <td><?= h($offeredAnswers->count) ?></td>
                <td><?= h($offeredAnswers->question_id) ?></td>
                <td class="actions">
                    <?= $this->Html->link(__('View'), ['controller' => 'OfferedAnswers', 'action' => 'view', $offeredAnswers->id]) ?>
                    <?= $this->Html->link(__('Edit'), ['controller' => 'OfferedAnswers', 'action' => 'edit', $offeredAnswers->id]) ?>
                    <?= $this->Form->postLink(__('Delete'), ['controller' => 'OfferedAnswers', 'action' => 'delete', $offeredAnswers->id], ['confirm' => __('Are you sure you want to delete # {0}?', $offeredAnswers->id)]) ?>
                </td>
            </tr>
            <?php endforeach; ?>
        </table>
        <?php endif; ?>
    </div>
</div>
