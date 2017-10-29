<br><br><br>
<div class="row">
    <div class="card-profile-stats">

        <div class="card-profile-stats-intro push-5">
            <img class="card-profile-stats-intro-pic" src="/img/default.jpg" alt="profile-image" />
        </div>
        <?php
        //TODO Center align the profile picture
        //TODO Dynamic Profile Picture
        //TODO Add Edit button for Description
        ?>

        <div class="text-center">
            <h3><?= $user->fullName?></h3>
            <p>Joined <?= $user->joinDate ?></small></p>
        </div>

        <hr/>

        <div class="card-profile-stats-container">
            <div class="card-profile-stats-statistic">
                <span class="stat"><?= $votedPolls ?></span>
                <p>Voted Polls</p>
            </div>
        </div>

        <div class="text-center">
            <p><?= $user->description ?></p>
        </div>
    </div
</div>