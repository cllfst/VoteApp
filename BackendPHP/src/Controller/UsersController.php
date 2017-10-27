<?php
namespace App\Controller;

use App\Controller\AppController;
use Cake\Auth\DefaultPasswordHasher;
use Cake\Event\Event;
use Cake\Mailer\Email;

/**
 * Users Controller
 *
 * @property \App\Model\Table\UsersTable $Users
 *
 * @method \App\Model\Entity\User[] paginate($object = null, array $settings = [])
 */
class UsersController extends AppController
{

//    public $paginate = [
//        'page' => 1,
//        'limit' => 5,
//        'maxLimit' => 15,
//        'sortWhitelist' => [
//            'id', 'name'
//        ]
//    ];


    /**
     * Index method
     *
     * @return \Cake\Http\Response|void
     */
    public function index()
    {
        $users = $this->paginate($this->Users);


        $this->set(compact('users'));
        $this->set('_serialize', ['users']);
    }

    /**
     * View method
     *
     * @param string|null $id User id.
     * @return \Cake\Http\Response|void
     * @throws \Cake\Datasource\Exception\RecordNotFoundException When record not found.
     */
    public function view($id = null)
    {
        $user = $this->Users->get($id, [
            'contain' => ['Answers']
        ]);

        $this->set('user', $user);
        $this->set('_serialize', ['user']);
    }

    /**
     * Add method
     *
     * @return \Cake\Http\Response|null Redirects on successful add, renders view otherwise.
     */
    public function add()
    {
        $user = $this->Users->newEntity();
        if ($this->request->is('post')) {
            $user = $this->Users->patchEntity($user, $this->request->getData());

            $password = $this->random_password(10);
            $user->password = $password;
            var_dump($password);
            if ($this->Users->save($user)) {

                $this->sendMail($user->email,['user'=> $user, 'password' => $password ],"Hello",'welcome');
                $this->Flash->success(__('The user has been saved.'));

                return $this->redirect(['action' => 'index']);
            }
            $this->Flash->error(__('The user could not be saved. Please, try again.'));
        }
        $this->set(compact('user'));
        $this->set('_serialize', ['user']);
    }

    /**
     * Edit method
     *
     * @param string|null $id User id.
     * @return \Cake\Http\Response|null Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null)
    {
        $user = $this->Users->get($id, [
            'contain' => []
        ]);
        if ($this->request->is(['put'])) {
            $user = $this->Users->patchEntity($user, $this->request->getData());
            if ($this->Users->save($user)) {
                $this->Flash->success(__('The user has been saved.'));

                return $this->redirect(['action' => 'index']);
            }
            $this->Flash->error(__('The user could not be saved. Please, try again.'));
        }
        $this->set(compact('user'));
        $this->set('_serialize', ['user']);
    }

    /**
     * Delete method
     *
     * @param string|null $id User id.
     * @return \Cake\Http\Response|null Redirects to index.
     * @throws \Cake\Datasource\Exception\RecordNotFoundException When record not found.
     */
    public function delete($id = null)
    {
        $this->request->allowMethod(['post', 'delete']);
        $user = $this->Users->get($id);
        if ($this->Users->delete($user)) {
            $this->Flash->success(__('The user has been deleted.'));
        } else {
            $this->Flash->error(__('The user could not be deleted. Please, try again.'));
        }

        return $this->redirect(['action' => 'index']);
    }


    public function random_password( $length = 8 ) {
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-=+;:,.?";
        $password = substr( str_shuffle( $chars ), 0, $length );
        return $password;
    }


    protected function sendMail($toMail,$vars, $subject, $template = 'default', $layout = 'default') {
        $email = new Email('default');
        $email
            ->template($template,$layout)
            ->emailFormat('html')
            ->to($toMail)
            ->viewVars(['vars' => $vars])
            ->subject($subject)
            ->send();
    }

    protected function userExist($email) {
        $userExist = $this->Users->find()
            ->select("email")
            ->where(['email' => $email])
            ->count();

        if ($userExist == 0)
            return false;
        return true;
    }

    public function login() {

        if ($this->request->is('post')) {

            if (!$this->userExist($this->request->getData('email'))) {
                $this->Flash->error('Email does not exist');

            } else {

                if ($this->Auth->identify()) {
                    $this->Auth->setUser($this->Auth->identify());
                    $this->Flash->success('logged in successfully');
                    $this->redirect('/users');

                } else {
                    $this->Flash->error('Incorrect credentials');
                }
            }
        }
    }

    public function logout() {

        $this->redirect($this->Auth->logout());

    }

//    public function beforeFilter(Event $event) {
//
//        $this->Auth->allow(['logout']);
//
//    }

    public function updatePassword() {

        $id = $this->Auth->user('id');
        $user = $this->Users->get($id, [
            'contain' => ['Answers']
        ]);
        $response = [
            "code" => 0,
            "message" => "Shit"];

        if ($this->request->is('put')) {
            $current_password = $this->request->getData('current_password');
            if ((new DefaultPasswordHasher())->check($current_password,$user->password)) {
                $user = $this->Users->patchEntity($user, $this->request->getData());
                if ($this->Users->save($user)) {
                    $response = [
                        "code" => 0,
                        "message" => "Password updated successfully"
                    ];
                } else {
                    $response = [
                        "code" => -1,
                        "message" => "Couldn\'t update password"
                    ];
                }
            } else {
                $response = [
                    "code" => -2,
                    "message" => "Current password is incorrect"
                ];
            }
        }


        $this->set(compact("response",$response));
        $this->set('_serialize',['response']);

    }

}
