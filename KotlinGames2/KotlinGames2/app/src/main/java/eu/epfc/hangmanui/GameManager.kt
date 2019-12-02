package eu.epfc.hangmanui

import android.content.Context
import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.random.Random

class GameManager {
    enum class GameState{
        Running,GameOverWin, GameOverLose
    }
    var tryCount = 0
    var wordToGuess = ""
    var maskedWord = ""
    var playedLetters = mutableListOf<Char>()
    var gameState: GameState = GameState.GameOverWin

    fun startNewGame(context: Context) {
        wordToGuess = generateWordToGuess(context)
        playedLetters.clear()
        maskedWord = getMaskedWordToGuess(wordToGuess,playedLetters)
        tryCount = 0
        gameState =GameState.Running
    }
    fun playLetter(letter: Char){

        tryCount ++



        if (tryCount>=6){
            gameState = GameState.GameOverLose
        }
    }
    private fun getMaskedWordToGuess(wordToGuess: String, letters: MutableList<Char>): String {
        var rep = ""
        val longueur : Int =wordToGuess.length
        for (ind in 0 until longueur) {
            if (letters.contains(wordToGuess[ind])){
                rep += wordToGuess[ind]
            }else {
                rep += '*'
            }
        }
        return rep
    }

    private fun generateWordToGuess(context: Context): String {
        val assetManager: AssetManager = context.assets //1
        val inputStream: InputStream = assetManager.open("dictionary.txt") //2
        val reader = BufferedReader(InputStreamReader(inputStream)) //3
        val wordList: MutableList<String> = mutableListOf()
        while (reader.ready()) {
            val line = reader.readLine()
            wordList.add(line) //4
        }
        val randomIndex = Random.nextInt(wordList.count() - 1)
        return wordList[randomIndex].toLowerCase()
    }
}