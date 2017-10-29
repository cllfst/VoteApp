<?php

use Cake\Core\Plugin;
use Cake\Routing\RouteBuilder;
use Cake\Routing\Router;
use Cake\Routing\Route\DashedRoute;

Router::defaultRouteClass(DashedRoute::class);

Router::prefix('api',function ($routes){


    $routes->fallbacks('InflectedRoute');

    Router::connect('/api/register', ['controller' => 'Users', 'action' => 'add', 'prefix' => 'api']);
    Router::connect('/api/token', ['controller' => 'Users', 'action' => 'token', 'prefix' => 'api']);

    Router::connect('/api/getPollsList', ['controller' => 'Polls', 'action' => 'getPollsList', 'prefix' => 'api']);
    Router::connect('/api/getPollQuestions/:id', ['controller' => 'Polls', 'action' => 'getPollQuestions', 'prefix' => 'api']);
    Router::connect('/api/getQuestionChoices/:id', ['controller' => 'Questions', 'action' => 'getQuestionChoices', 'prefix' => 'api']);

    Router::connect('/api/vote/',['controller' => 'OfferedAnswers', 'action' => 'vote', 'prefix' => 'api']);
    Router::connect('/api/didVote/:id',['controller' => 'OfferedAnswers', 'action' => 'didVoteAPI', 'prefix' => 'api']);
});

Router::scope('/', function (RouteBuilder $routes) {

    $routes->connect('/', ['controller' => 'Polls', 'action' => 'index']);
    $routes->fallbacks(DashedRoute::class);
});
Plugin::routes();
