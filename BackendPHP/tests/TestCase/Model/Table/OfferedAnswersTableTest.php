<?php
namespace App\Test\TestCase\Model\Table;

use App\Model\Table\OfferedAnswersTable;
use Cake\ORM\TableRegistry;
use Cake\TestSuite\TestCase;

/**
 * App\Model\Table\OfferedAnswersTable Test Case
 */
class OfferedAnswersTableTest extends TestCase
{

    /**
     * Test subject
     *
     * @var \App\Model\Table\OfferedAnswersTable
     */
    public $OfferedAnswers;

    /**
     * Fixtures
     *
     * @var array
     */
    public $fixtures = [
        'app.offered_answers',
        'app.questions',
        'app.polls',
        'app.answers'
    ];

    /**
     * setUp method
     *
     * @return void
     */
    public function setUp()
    {
        parent::setUp();
        $config = TableRegistry::exists('OfferedAnswers') ? [] : ['className' => OfferedAnswersTable::class];
        $this->OfferedAnswers = TableRegistry::get('OfferedAnswers', $config);
    }

    /**
     * tearDown method
     *
     * @return void
     */
    public function tearDown()
    {
        unset($this->OfferedAnswers);

        parent::tearDown();
    }

    /**
     * Test initialize method
     *
     * @return void
     */
    public function testInitialize()
    {
        $this->markTestIncomplete('Not implemented yet.');
    }

    /**
     * Test validationDefault method
     *
     * @return void
     */
    public function testValidationDefault()
    {
        $this->markTestIncomplete('Not implemented yet.');
    }

    /**
     * Test buildRules method
     *
     * @return void
     */
    public function testBuildRules()
    {
        $this->markTestIncomplete('Not implemented yet.');
    }
}
