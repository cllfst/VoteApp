<?php
/**
 * Created by IntelliJ IDEA.
 * User: ichebbi
 * Date: 27/10/17
 * Time: 12:30
 */

namespace App\Controller\Api;


class UsersController extends AppController {

    public $paginate = [
        'page' => 1,
        'limit' => 5,
        'maxLimit' => 15,
        'sortWhitelist' => [
            'id', 'first_name'
        ]
    ];
}