<?php
namespace App\Controller;

use App\Controller\AppController;
use App\Model\Entity\Answer;

/**
 * OfferedAnswers Controller
 *
 * @property \App\Model\Table\OfferedAnswersTable $OfferedAnswers
 *
 * @method \App\Model\Entity\OfferedAnswer[] paginate($object = null, array $settings = [])
 */
class OfferedAnswersController extends AppController
{

    /**
     * Index method
     *
     * @return \Cake\Http\Response|void
     */
    public function index()
    {

        $this->paginate = [
            'contain' => ['Questions']
        ];
        $offeredAnswers = $this->paginate($this->OfferedAnswers);

        $this->set(compact('offeredAnswers'));
        $this->set('_serialize', ['offeredAnswers']);
    }

    /**
     * View method
     *
     * @param string|null $id Offered Answer id.
     * @return \Cake\Http\Response|void
     * @throws \Cake\Datasource\Exception\RecordNotFoundException When record not found.
     */
    public function view($id = null)
    {
        $offeredAnswer = $this->OfferedAnswers->get($id, [
            'contain' => ['Questions']
        ]);

        $this->set('offeredAnswer', $offeredAnswer);
        $this->set('_serialize', ['offeredAnswer']);
    }

    /**
     * Add method
     *
     * @return \Cake\Http\Response|null Redirects on successful add, renders view otherwise.
     */
    public function add()
    {
        $offeredAnswer = $this->OfferedAnswers->newEntity();
        if ($this->request->is('post')) {
            $offeredAnswer = $this->OfferedAnswers->patchEntity($offeredAnswer, $this->request->getData());
            if ($this->OfferedAnswers->save($offeredAnswer)) {
                $this->Flash->success(__('The offered answer has been saved.'));

                return $this->redirect(['action' => 'index']);
            }
            $this->Flash->error(__('The offered answer could not be saved. Please, try again.'));
        }
        $questions = $this->OfferedAnswers->Questions->find('list', ['limit' => 200]);
        $this->set(compact('offeredAnswer', 'questions'));
        $this->set('_serialize', ['offeredAnswer']);
    }

    /**
     * Edit method
     *
     * @param string|null $id Offered Answer id.
     * @return \Cake\Http\Response|null Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null)
    {
        $offeredAnswer = $this->OfferedAnswers->get($id, [
            'contain' => []
        ]);

        if ($this->request->is(['patch', 'post', 'put'])) {
            $offeredAnswer = $this->OfferedAnswers->patchEntity($offeredAnswer, $this->request->getData());
            if ($this->OfferedAnswers->save($offeredAnswer)) {
                $this->Flash->success(__('The offered answer has been saved.'));

                return $this->redirect(['action' => 'index']);
            }
            $this->Flash->error(__('The offered answer could not be saved. Please, try again.'));
        }

        $questions = $this->OfferedAnswers->Questions->find('list', ['limit' => 200]);
        $this->set(compact('offeredAnswer', 'questions'));
        $this->set('_serialize', ['offeredAnswer']);
    }

    /**
     * Delete method
     *
     * @param string|null $id Offered Answer id.
     * @return \Cake\Http\Response|null Redirects to index.
     * @throws \Cake\Datasource\Exception\RecordNotFoundException When record not found.
     */
    public function delete($id = null)
    {
        $this->request->allowMethod(['post', 'delete']);
        $offeredAnswer = $this->OfferedAnswers->get($id);
        if ($this->OfferedAnswers->delete($offeredAnswer)) {
            $this->Flash->success(__('The offered answer has been deleted.'));
        } else {
            $this->Flash->error(__('The offered answer could not be deleted. Please, try again.'));
        }

        return $this->redirect(['action' => 'index']);
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

    public function didVoteAPI($poll_id = null) {

        if (!$poll_id) {
            $poll_id = $this->request->getParam('id');
        }

        if ($this->didVote($poll_id)) {
            $response = [
                'didVote' => true
            ];
        } else
            $response = [
                'didVote' => false
            ];

        $this->set(compact('response',$response));
        $this->set('_serialize', ['response']);
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
            $response = ['success' => true];
        } else
            $response = ['success' => false];

        $this->set(compact('response',$response));
        $this->set('_serialize', ['response']);

    }

    public function vote($id = null) {

        if (!$id) {
            $id = $this->request->getParam('id');
        }

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

           if ($this->OfferedAnswers->save($offeredAnswer)) {

               $this->votedOnPoll($poll_id);

               $response = ["success" => true];
           } else
               $response = ["success" => false];

       } else
           $response = [
               "success" => false,
               "error" => "Did vote already"
           ];


        $this->set(compact('response',$response));
        $this->set('_serialize', ['response']);
    }
}
