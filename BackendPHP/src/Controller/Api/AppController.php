<?php
/**
 * Created by IntelliJ IDEA.
 * User: ichebbi
 * Date: 27/10/17
 * Time: 12:32
 */

namespace App\Controller\Api;
use Cake\Controller\Controller;


class AppController extends Controller {

    use \Crud\Controller\ControllerTrait;

    public function initialize()
    {
        parent::initialize();

        $this->loadComponent('RequestHandler');
        $this->loadComponent('Crud.Crud',[
            'actions' => [
                'Crud.Index',
                'Crud.View',
                'Crud.Add',
                'Crud.Edit',
                'Crud.Delete'
            ],
            'listeners' => [
                'Crud.Api',
                'Crud.ApiPagination'
            ]
        ]);

    }
}