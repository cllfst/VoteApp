<?php
/**
 * Created by IntelliJ IDEA.
 * User: ichebbi
 * Date: 29/10/17
 * Time: 02:21
 */

namespace App\Controller\Api;


class QuestionsController extends  AppController {

    public function getQuestionChoices () {

        $this->request->allowMethod(['get']);
        $id = $this->request->getParam('id');

        $question = $this->Questions->get($id, [
            'contain' => ['Polls', 'OfferedAnswers']
        ]);

        $this->set([
            'code' => '0',
            'data' => $question,
            '_serialize'=> ['code','data']
        ]);

    }

}