<?php
/**
 * Created by IntelliJ IDEA.
 * User: ichebbi
 * Date: 27/10/17
 * Time: 12:30
 */

namespace App\Controller\Api;


use Cake\Auth\DefaultPasswordHasher;
use Cake\Mailer\Email;
use Cake\Utility\Security;
use Firebase\JWT\JWT;

class UsersController extends AppController {

    public function initialize()
    {
        parent::initialize();
        $this->Auth->allow(['add','token']);
    }

    public function add() {

        $this->request->allowMethod('post');
        $user = $this->Users->newEntity();
        if ($this->request->is('post')) {

            if ($this->userExist($this->request->getData('email'))) {
                $this->set([
                    'code' => -1,
                    'data' => [
                        'message' => 'This Email is registered already'
                    ]

                ]);
            } else {
                $user = $this->Users->patchEntity($user, $this->request->getData());
//                $password = $this->random_password(10);
                $password = 'root';
                $user->password = $password;

                if ($this->Users->save($user)) {
                    $this->sendMail($user->email,['user'=> $user, 'password' => $password ],"Hello",'welcome');
                    $this->set([
                        'code' => 0,
                        'data' => [
                            'message' => 'Registered Successfully',
                            'token' => JWT::encode(
                                [
                                    'sub' => $user->id,
                                ],
                                Security::salt())
                        ]
                    ]);
                }
                else {
                    $this->set([
                        'code' => -2,
                        'data' => [
                            'message' => 'Can\'t register the user'
                        ]
                    ]);
                }
            }
        }
        $this->set('_serialize', ['code','data']);
    }

    public function token()
    {
        $this->request->allowMethod('post');
        $user = $this->Auth->identify();

        if ($user) {
            $this->set([
                'code' => 0,
                'data' => [
                    'token' => JWT::encode([
                        'sub' => $user['id']
                    ],
                        Security::salt())
                ]
            ]);
        } else {

            if (!$this->userExist($this->request->getData('email'))) {
                $this->set([
                    'code' => -1,
                    'data' => [
                        'message' => 'Email is not registered'
                    ]
                ]);
            } else {
                $this->set([
                    'code' => -2,
                    'data' => [
                        'message' => 'Invalid Credentials'
                    ]
                ]);
            }
        }

        $this->set('_serialize', ['code','data']);

    }

    public function test() {
        $user = $this->Auth->user();
        $this->set([
            'user'=> $user,
            '_serialize' => ['user']
        ]);

    }



    public function updatePassword() {

        $this->request->allowMethod('put');
        $id = $this->Auth->user('id');
        $user = $this->Users->get($id, [
            'contain' => ['Answers']
        ]);

        if ($this->request->is('put')) {
            $current_password = $this->request->getData('current_password');
            if ((new DefaultPasswordHasher())->check($current_password,$user->password)) {
                $user = $this->Users->patchEntity($user, $this->request->getData());
                if ($this->Users->save($user)) {
                    $this->set([
                        "code" => 0,
                        'data' => [
                            "message" => "Password updated successfully"
                        ]
                    ]);
                } else {
                    $this->set([
                        "code" => -1,
                        'data' => [
                            "message" => "Cannot update password"
                        ]
                    ]);
                }
            } else {
                $this->set([
                    "code" => -2,
                    'data' => [
                        "message" => "Current password is incorrect"
                    ]
                ]);
            }
        }


        $this->set('_serialize',['code','data']);

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

    protected function random_password( $length = 8 ) {
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-=+;:,.?";
        $password = substr( str_shuffle( $chars ), 0, $length );
        return $password;
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

}