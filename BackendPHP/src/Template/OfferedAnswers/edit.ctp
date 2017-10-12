<?php
/**
 * @var \App\View\AppView $this
 */
?>
<nav class="large-3 medium-4 columns" id="actions-sidebar">
    <ul class="side-nav">
        <li class="heading"><?= __('Actions') ?></li>
        <li><?= $this->Form->postLink(
                __('Delete'),
                ['action' => 'delete', $offeredAnswer->id],
                ['confirm' => __('Are you sure you want to delete # {0}?', $offeredAnswer->id)]
            )
        ?></li>
        <li><?= $this->Html->link(__('List Offered Answers'), ['action' => 'index']) ?></li>
        <li><?= $this->Html->link(__('List Questions'), ['controller' => 'Questions', 'action' => 'index']) ?></li>
        <li><?= $this->Html->link(__('New Question'), ['controller' => 'Questions', 'action' => 'add']) ?></li>
    </ul>
</nav>
<div class="offeredAnswers form large-9 medium-8 columns content">
    <?= $this->Form->create($offeredAnswer) ?>
    <fieldset>
        <legend><?= __('Edit Offered Answer') ?></legend>
        <?php
            echo $this->Form->control('answer_text');
            echo $this->Form->control('count');
            echo $this->Form->control('question_id', ['options' => $questions]);
        ?>
    </fieldset>
    <?= $this->Form->button(__('Submit')) ?>
    <?= $this->Form->end() ?>
</div>
