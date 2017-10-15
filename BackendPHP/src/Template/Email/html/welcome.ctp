<h1> Welcome Motherfucker <?= $vars['user']['first_name']?> </h1>
<p> Guess what you password is <?= $vars['password'] ?> </p>
<?php
echo '<pre>';
var_dump($vars);
echo '</pre>';
?>