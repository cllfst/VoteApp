<?php
/**
 * Created by IntelliJ IDEA.
 * User: ichebbi
 * Date: 29/10/17
 * Time: 01:52
 */

namespace App\Controller\Api;


class OfferedAnswersController extends AppController {

    public function didVote($poll_id) {

        $user_id = $this->Auth->user('id');
        $Answer = $this->loadModel('Answers');

        $voteCount = $Answer->find()
            ->select()
            ->where(['user_id' => $user_id,'poll_id' => $poll_id])
            ->count();

        if ($voteCount > 0) {
            return true;
        } else
            return false;

    }

    public function didVoteAPI() {

        $this->request->allowMethod(['get']);
        $poll_id = $this->request->getParam('id');

        if ($this->didVote($poll_id)) {
            $this->set([
                "code" => -1,
                'data' => [
                    'message' => "Did vote already"
                ],
                '_serialize', ['code','data']
            ]);
        } else
            $this->set([
                "code" => 0,
                'data' => [
                    'message' => 'didn\'t vote'
                ],
                '_serialize', ['code','data']
            ]);
    }

    public function votedOnPoll($poll_id = null) {

        if (!$poll_id) {
            $poll_id = $this->request->getParam('id');
        }

        $Answers = $this->loadModel('Answers');

        $entity = $Answers->newEntity();

        $data = [
            'poll_id'=> $poll_id,
            'user_id' => $this->Auth->user('id')
        ];

        $entity = $Answers->patchEntity($entity,$data);

        if ($Answers->save($entity)) {
            return true;
        } else
            return false;
    }

    public function vote() {

        $this->request->allowMethod(['get']);

        $id = $this->request->getParam('id');
        $offeredAnswer = $this->OfferedAnswers->get($id);


        $Polls = $this->loadModel('questions');
        $poll_id = $Polls->find()
            ->select('poll_id')
            ->where(['id' => $offeredAnswer->question_id])
            ->first()['poll_id'];


        if (!$this->didVote($poll_id)) {

            $offeredAnswer = $this->OfferedAnswers->get($id, [
                'contain' => []
            ]);

            $offeredAnswer->count = $offeredAnswer->count + 1;

            if ($this->OfferedAnswers->save($offeredAnswer) && $this->votedOnPoll($poll_id) ) {

                $this->set([
                    'code' => 0,
                    'data' => [
                        'message' => "Voted successfully"
                    ]
                ]);

            } else
                $this->set([
                    'code' => -1,
                    'data' => [
                        'message' => 'Couldn\'t vote'
                    ]
                ]);

        } else
            $this->set([
                "code" => -2,
                'data' => [
                    "message" => "Did vote already"
                ]
            ]);


        $this->set('_serialize', ['code','data']);
    }

}