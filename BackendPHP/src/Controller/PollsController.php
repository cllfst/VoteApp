<?php
namespace App\Controller;

use App\Controller\AppController;

/**
 * Polls Controller
 *
 * @property \App\Model\Table\PollsTable $Polls
 *
 * @method \App\Model\Entity\Poll[] paginate($object = null, array $settings = [])
 */
class PollsController extends AppController {

    /**
     * Index method
     *
     * @return \Cake\Http\Response|void
     */
    public function index()
    {
        $this->request->allowMethod(['get']);
        $polls = $this->paginate($this->Polls);

        $this->set(compact('polls'));
        $this->set('_serialize', ['polls']);
    }

    protected function getQuestionChoices($question_id) {

        $this->loadModel('Questions');
        $questions = $this->Questions->get($question_id, [
            'contain' => ['Polls', 'OfferedAnswers']
        ]);
        return $questions->offered_answers;
    }

    /**
     * View method
     *    $this->request->allowMethod(['post', 'delete']);

     * @param string|null $id Poll id.
     * @return \Cake\Http\Response|void
     * @throws \Cake\Datasource\Exception\RecordNotFoundException When record not found.
     */
    public function view($id = null )
    {
        if (!$id) {
            $id = $this->request->getParam('id');
        }
        $poll = $this->Polls->get($id, [
            'contain' => ['Answers', 'Questions']
        ]);

        if ($poll->is_open && $this->didVote($poll->id)) {
            $this->Flash->set("You already voted");
            $this->redirect('/');
        }

        $questions = $poll->questions;
        $choices = [];
        foreach ($questions as $question) {
            array_push($choices,$this->getQuestionChoices($question->id));
        }

        if ($this->request->is('post')) {
            $answers = $this->request->getData();
            $this->vote($answers);
        }

//        if (!$poll->is_open) {
//            $OfferedAnswers = $this->loadModel('OfferedAnswers');
//            $totalCount = $OfferedAnswers->find()
//                ->select('count')
//                ->where(['question_id' => '89b83179-7c85-445f-a835-3be529f114f1'])
//                ->sumOf('count');
//            var_dump($totalCount);
//        }

        $this->set([
            'poll' => $poll,
            'choices' => $choices
        ]);
        $this->set('_serialize', ['poll','choices']);
    }

    /**
     * Add method
     *
     * @return \Cake\Http\Response|null Redirects on successful add, renders view otherwise.
     */
    public function add() {

        if (!$this->viewVars['isAdmin']) {
            $this->Flash->set("You're not Authorized");
            $this->redirect('/');
        }

        $poll = $this->Polls->newEntity();
        if ($this->request->is('post')) {
            $poll = $this->Polls->patchEntity($poll, $this->request->getData());
            if ($this->Polls->save($poll)) {
                $this->Flash->success(__('The poll has been saved.'));

                return $this->redirect(['action' => 'index']);
            }
            $this->Flash->error(__('The poll could not be saved. Please, try again.'));
        }
        $this->set(compact('poll'));
        $this->set('_serialize', ['poll']);
    }

    /**
     * Edit method
     *
     * @param string|null $id Poll id.
     * @return \Cake\Http\Response|null Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null) {

        if (!$this->viewVars['isAdmin']) {
            $this->Flash->set("You're not Authorized");
            $this->redirect('/');
        }

        $poll = $this->Polls->get($id, [
            'contain' => []
        ]);
        if ($this->request->is(['patch', 'post', 'put'])) {
            $poll = $this->Polls->patchEntity($poll, $this->request->getData());
            if ($this->Polls->save($poll)) {
                $this->Flash->success(__('The poll has been saved.'));

                return $this->redirect(['action' => 'index']);
            }
            $this->Flash->error(__('The poll could not be saved. Please, try again.'));
        }
        $this->set(compact('poll'));
        $this->set('_serialize', ['poll']);
    }

    /**
     * Delete method
     *
     * @param string|null $id Poll id.
     * @return \Cake\Http\Response|null Redirects to index.
     * @throws \Cake\Datasource\Exception\RecordNotFoundException When record not found.
     */
    public function delete($id = null) {

        if (!$this->viewVars['isAdmin']) {
            $this->Flash->set("You're not Authorized");
            $this->redirect('/');
        }

        $this->request->allowMethod(['post', 'delete']);
        $poll = $this->Polls->get($id);
        if ($this->Polls->delete($poll)) {
            $this->Flash->success(__('The poll has been deleted.'));
        } else {
            $this->Flash->error(__('The poll could not be deleted. Please, try again.'));
        }

        return $this->redirect(['action' => 'index']);
    }


    public function vote($choices = []) {

        $this->loadModel('OfferedAnswers');

        $choice1 = $this->getFirstValue($choices);

        $question_id = $this->getQuestion($choice1);

        $poll_id = $this->getPoll($question_id);

        if ($this->didVote($poll_id)) {
            $this->Flash->error("Did vote already");
            $this->redirect('/');
        } else {

            foreach ($choices as $question => $choice) {
                $offeredAnswer = $this->OfferedAnswers->get($choice);
                $offeredAnswer->count = $offeredAnswer->count +1;

                if (!$this->OfferedAnswers->save($offeredAnswer)) {
                    $this->Flash->error("Couldn't vote");
                    $this->redirect('/');
                    exit(0);
                }
            }
            $this->votedOnPoll($poll_id);
        }
    }

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


    public function votedOnPoll($poll_id) {

        $Answers = $this->loadModel('Answers');
        $entity = $Answers->newEntity();

        $data = [
            'poll_id'=> $poll_id,
            'user_id' => $this->Auth->user('id')
        ];

        $entity = $Answers->patchEntity($entity,$data);

        if ($Answers->save($entity)) {
            $this->Flash->success("Voted successfully");
            return true;
        } else {
            $this->Flash->error("Couldn't Vote");
            return false;
        }
    }

    protected function getPoll($question_id) {

        $Polls = $this->loadModel('questions');

        $poll_id = $Polls->find()
            ->select('poll_id')
            ->where(['id' => $question_id])
            ->first()['poll_id'];
        return $poll_id;
    }

    protected function getQuestion($choice_id) {
        $OfferedAnswers = $this->loadModel('OfferedAnswers');
        $question_id  = $OfferedAnswers->find()
            ->select('question_id')
            ->where(['id' => $choice_id])
            ->first()['question_id'];
        return $question_id;
    }

    protected function getFirstValue($arr = []) {
        reset($arr);
        return $arr[key($arr)];
    }

}
