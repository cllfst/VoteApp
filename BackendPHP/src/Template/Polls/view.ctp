<br><br><br>

<div class="row text-center">

    <h1><?= $poll->text ?></h1>

    <?php if($poll->is_open): ?>
        <?= $this->Form->create() ?>
        <div class="polls">
            <?php $i=0 ?>
            <?php foreach ($poll->questions as $question): ?>
                <h5 class="polls-question">
                    <span class="polls-question-label">Q:</span>
                    <?= $question->question_text ?>
                </h5>
                <div class="polls-options">
                    <?php foreach ($choices[$i] as $choice): ?>

                        <div>
                            <input type="radio" name="<?= $question->id ?>" value="<?= $choice->id ?>" id="<?= $choice->id ?>" required>
                            <label for="choice"><?= $choice->answer_text ?></label>
                        </div>
                    <?php endforeach; ?>
                    <?php $i++ ?>

                </div>
            <?php endforeach; ?>

        </div>
        <div class="polls-submit">
            <?= $this->Form->submit('Submit Vote',['class' => 'button']) ?>
        </div>
        <?= $this->Form->end() ?>

    <?php else: ?>


        <div class="polls">
            <?php $i=0 ?>
            <?php foreach ($poll->questions as $question): ?>
                <h5 class="polls-question">
                    <span class="polls-question-label">Q:</span>
                    <?= $question->question_text ?>
                </h5>
                <div class="polls-options">
                    <div class="span6">
                        <?php foreach ($choices[$i] as $choice): ?>

                            <strong><?= $choice->answer_text?></strong><span class="pull-right"> 30%</span>
                            <br>
                            <div class="push-3">
                                <div class="" id="myProgress">
                                    <div class="progress-meter" id="myBar" style="width: 100%;"></div>
                                </div>
                            </div>
                            <br>
                        <?php endforeach; ?>
                        <?php $i++ ?>
                    </div>
                </div>
            <?php endforeach; ?>

        </div>
    <?php endif; ?>


    <!--    <div>-->
    <!--        <div id="myProgress">-->
    <!--            <div id="myBar"></div>-->
    <!--        </div>-->
    <!--    </div>-->


    <!--    <div class="span6">-->
    <!--        <h5>Poll: Where do you usually browse</h5>-->
    <!--        <strong>Windows PC</strong><span class="pull-right">30%</span>-->
    <!--        <div class="progress danger active">-->
    <!--            <div class="progress-meter" id="myBar" style="width: 30%;"></div>-->
    <!--        </div>-->
    <!--        <strong>Mac</strong><span class="pull-right">40%</span>-->
    <!--        <div class="progress success active">-->
    <!--            <div class="progress-meter" id="myBar" style="width: 40%;background-color: red"></div>-->
    <!--        </div>-->
    <!--        <strong>iPad/iPhone</strong><span class="pull-right">10%</span>-->
    <!--        <div class="progress progress-striped active">-->
    <!--            <div class="bar" style="width: 10%;"></div>-->
    <!--        </div>-->
    <!--        <strong>Android</strong><span class="pull-right">5%</span>-->
    <!--        <div class="progress progress-success active">-->
    <!--            <div class="bar" style="width: 5%;"></div>-->
    <!--        </div>-->
    <!--        <strong>Others</strong><span class="pull-right">15%</span>-->
    <!--        <div class="progress progress-warning active">-->
    <!--            <div class="bar" style="width: 15%;"></div>-->
    <!--        </div>-->
    <!--        <p>-->
    <!--            <a href="#" class="btn btn-large btn-success">Vote</a>-->
    <!--            <a href="#" class="pull-right">View detailed results</a>-->
    <!--        </p>-->
    <!--    </div>-->