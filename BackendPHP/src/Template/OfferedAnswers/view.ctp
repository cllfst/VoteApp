<?php
/**
 * @var \App\View\AppView $this
 * @var \App\Model\Entity\OfferedAnswer $offeredAnswer
 */
?>
<nav class="large-3 medium-4 columns" id="actions-sidebar">
    <ul class="side-nav">
        <li class="heading"><?= __('Actions') ?></li>
        <li><?= $this->Html->link(__('Edit Offered Answer'), ['action' => 'edit', $offeredAnswer->id]) ?> </li>
        <li><?= $this->Form->postLink(__('Delete Offered Answer'), ['action' => 'delete', $offeredAnswer->id], ['confirm' => __('Are you sure you want to delete # {0}?', $offeredAnswer->id)]) ?> </li>
        <li><?= $this->Html->link(__('List Offered Answers'), ['action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Offered Answer'), ['action' => 'add']) ?> </li>
        <li><?= $this->Html->link(__('List Questions'), ['controller' => 'Questions', 'action' => 'index']) ?> </li>
        <li><?= $this->Html->link(__('New Question'), ['controller' => 'Questions', 'action' => 'add']) ?> </li>
    </ul>
</nav>
<div class="offeredAnswers view large-9 medium-8 columns content">
    <h3><?= h($offeredAnswer->id) ?></h3>
    <table class="vertical-table">
        <tr>
            <th scope="row"><?= __('Id') ?></th>
            <td><?= h($offeredAnswer->id) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Answer Text') ?></th>
            <td><?= h($offeredAnswer->answer_text) ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Question') ?></th>
            <td><?= $offeredAnswer->has('question') ? $this->Html->link($offeredAnswer->question->id, ['controller' => 'Questions', 'action' => 'view', $offeredAnswer->question->id]) : '' ?></td>
        </tr>
        <tr>
            <th scope="row"><?= __('Count') ?></th>
            <td><?= $this->Number->format($offeredAnswer->count) ?></td>
        </tr>
    </table>
</div>
