<?php
/**
 * Created by IntelliJ IDEA.
 * User: ichebbi
 * Date: 29/10/17
 * Time: 00:42
 */

namespace App\Controller\Api;


class PollsController extends AppController
{

    public function getPollsList()
    {

        $this->request->allowMethod(['get']);
        $polls = $this->paginate($this->Polls);

        $this->set([
            'code' => 0,
            'data' => $polls,
            '_serialize' => ['code', 'data']
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
            'data' => $poll,
            '_serialize' => ['code', 'data']
        ]);
    }

    public function toggleOpen()
    {

        if ($this->request->is('post')) {

            $pollId = $this->request->getParam('id');

            $poll = $this->Polls->get($pollId, ['contain' => []]);
            $poll = $this->Polls->patchEntity($poll, $this->request->getData());

            if ($this->Polls->save($poll)) {

                $this->set([
                    "code" => 0,
                    'data' => [
                        "message" => "Poll updated successfully",
                        "Poll State" => $poll['is_open']
                    ]
                ]);
            } else {

                $this->set([
                    "code" => -1,
                    'data' => [
                        "message" => "Poll update failed",
                        "Poll State" => $poll['is_open']
                    ]
                ]);
            }
        }

        $this->set('_serialize', ['code', 'data']);
    }
}