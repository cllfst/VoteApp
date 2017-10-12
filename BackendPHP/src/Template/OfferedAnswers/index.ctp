<?php
/**
 * @var \App\View\AppView $this
 * @var \App\Model\Entity\OfferedAnswer[]|\Cake\Collection\CollectionInterface $offeredAnswers
 */
?>
<nav class="large-3 medium-4 columns" id="actions-sidebar">
    <ul class="side-nav">
        <li class="heading"><?= __('Actions') ?></li>
        <li><?= $this->Html->link(__('New Offered Answer'), ['action' => 'add']) ?></li>
        <li><?= $this->Html->link(__('List Questions'), ['controller' => 'Questions', 'action' => 'index']) ?></li>
        <li><?= $this->Html->link(__('New Question'), ['controller' => 'Questions', 'action' => 'add']) ?></li>
    </ul>
</nav>
<div class="offeredAnswers index large-9 medium-8 columns content">
    <h3><?= __('Offered Answers') ?></h3>
    <table cellpadding="0" cellspacing="0">
        <thead>
            <tr>
                <th scope="col"><?= $this->Paginator->sort('id') ?></th>
                <th scope="col"><?= $this->Paginator->sort('answer_text') ?></th>
                <th scope="col"><?= $this->Paginator->sort('count') ?></th>
                <th scope="col"><?= $this->Paginator->sort('question_id') ?></th>
                <th scope="col" class="actions"><?= __('Actions') ?></th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($offeredAnswers as $offeredAnswer): ?>
            <tr>
                <td><?= h($offeredAnswer->id) ?></td>
                <td><?= h($offeredAnswer->answer_text) ?></td>
                <td><?= $this->Number->format($offeredAnswer->count) ?></td>
                <td><?= $offeredAnswer->has('question') ? $this->Html->link($offeredAnswer->question->id, ['controller' => 'Questions', 'action' => 'view', $offeredAnswer->question->id]) : '' ?></td>
                <td class="actions">
                    <?= $this->Html->link(__('View'), ['action' => 'view', $offeredAnswer->id]) ?>
                    <?= $this->Html->link(__('Edit'), ['action' => 'edit', $offeredAnswer->id]) ?>
                    <?= $this->Form->postLink(__('Delete'), ['action' => 'delete', $offeredAnswer->id], ['confirm' => __('Are you sure you want to delete # {0}?', $offeredAnswer->id)]) ?>
                </td>
            </tr>
            <?php endforeach; ?>
        </tbody>
    </table>
    <div class="paginator">
        <ul class="pagination">
            <?= $this->Paginator->first('<< ' . __('first')) ?>
            <?= $this->Paginator->prev('< ' . __('previous')) ?>
            <?= $this->Paginator->numbers() ?>
            <?= $this->Paginator->next(__('next') . ' >') ?>
            <?= $this->Paginator->last(__('last') . ' >>') ?>
        </ul>
        <p><?= $this->Paginator->counter(['format' => __('Page {{page}} of {{pages}}, showing {{current}} record(s) out of {{count}} total')]) ?></p>
    </div>
</div>
