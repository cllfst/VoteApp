<?php
/**
 * Created by IntelliJ IDEA.
 * User: ichebbi
 * Date: 29/10/17
 * Time: 00:42
 */

namespace App\Controller\Api;


class PollsController extends AppController {

    public function getPollsList() {

        $this->request->allowMethod(['get']);
        $polls = $this->paginate($this->Polls);

        $this->set([
            'code' => 0,
            'data'=> $polls,
            '_serialize' => ['code','data']
        ]);

    }

    public function getPollQuestions()
    {
        $this->request->allowMethod(['get']);
        $id = $this->request->getParam('id');

        $poll = $this->Polls->get($id, [
            'contain' => ['Answers', 'Questions']
        ]);

        $this->set([
            'code' => 0,
            'data'=> $poll,
            '_serialize'=> ['code','data']
        ]);
    }

}